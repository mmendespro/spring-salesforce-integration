package br.com.vivo.sfclient.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vivo.sfclient.exceptions.MultipleEntityRecord400ResponseException;
import br.com.vivo.sfclient.exceptions.ServiceException;
import br.com.vivo.sfclient.filters.WebClientFilter;
import br.com.vivo.sfclient.model.MultipleEntityRecord201Response;
import br.com.vivo.sfclient.model.MultipleEntityRecordRequest;
import br.com.vivo.sfclient.model.auth.SalesforceLoginResult;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Intended for bulk inserts into Salesforce, with just one API call consumed for up to 200 records max. SF docs:
 * https://developer.salesforce.com/docs/atlas.en-us.240.0.api_rest.meta/api_rest/dome_composite_sobject_tree_flat.htm
 *
 * Sample call:
 * https://developer.salesforce.com/docs/atlas.en-us.240.0.api_rest.meta/api_rest/dome_composite_sobject_tree_flat.htm
 * Note attribute type and unique referenceID (can be any value, just needs to be unique per call) *required*
 *
 * See test cases for basic operation.
 *
 * Success 201 Created response gives SF IDs of created objects per reference ID provided above:
 * Item viewable in Salesforce UI by plugging in an id below, e.g.:
 * https://yourinstance.lightning.force.com/a882i0000008cWAAAI
 * {
 *     "hasErrors": false,
 *     "results": [
 *         {
 *             "referenceId": "anyUniqueID1",
 *             "id": "a882i0000008cWAAAI"
 *         },
 *         {
 *             "referenceId": "anyUniqueID2",
 *             "id": "a882i0000008cWAAAB"
 *         }
 *     ]
 * }
 *
 * Generic error response 403 if reference IDs missing:
 * [
 *     {
 *         "message": "Include a reference ID for each record in the request.",
 *         "errorCode": "INVALID_INPUT"
 *     }
 * ]
 *
 * Usual error response 400 otherwise:
 *
 * {
 *     "hasErrors": true,
 *     "results": [
 *         {
 *             "referenceId": "a882i0000008cWAAAI",
 *             "errors": [
 *                 {
 *                     "statusCode": "INVALID_INPUT",
 *                     "message": "Duplicate ReferenceId provided in the request.",
 *                     "fields": []
 *                 }
 *             ]
 *         },
 *         {
 *             "referenceId": "a882i0000008cWAAAB",
 *             "errors": [
 *                 {
 *                     "statusCode": "INVALID_INPUT",
 *                     "message": "Include an entity type for each record in the request.",
 *                     "fields": []
 *                 }
 *             ]
 *         }
 *     ]
 * }
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "salesforce.client.enabled", matchIfMissing = true)
public class SalesforceMultipleRecordInserter extends AbstractRESTService {

    private final String API_VERSION = "v56.0";
    private final SalesforceAuthService salesforceAuthService;

    public SalesforceMultipleRecordInserter(ObjectMapper objectMapper, SalesforceAuthService salesforceAuthService) {
        super(null, objectMapper);
        this.salesforceAuthService = salesforceAuthService;
        setWebClient(WebClient.builder().filter(WebClientFilter.handleErrors(this::getMonoClientResponse)).build());
    }

    public MultipleEntityRecord201Response bulkInsert(String entity, MultipleEntityRecordRequest<?> multiRequest) throws JsonProcessingException {
        
        String jsonString = objectMapper.writeValueAsString(multiRequest);

        // Auth in Salesforce
        SalesforceLoginResult sfAuthResult = salesforceAuthService.loginSalesforce();

        return webClient
                .post()
                .uri(sfAuthResult.getInstanceUrl() + "/services/data/" + API_VERSION + "/composite")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonString)
                .retrieve()
                .bodyToMono(MultipleEntityRecord201Response.class)
                // retry of 1: if access token expired, will be removed after
                // first failed call and obtained & used during second.
                // Can confirm by revoking token in Salesforce (Setup: Security: Session Management screen)
                // and comparing results vs. retry of 0
                .retry(1)
                .block();
    }

    public Mono<ClientResponse> getMonoClientResponse(ClientResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.statusCode().value());

        if (BAD_REQUEST.equals(status)) {
            return response.bodyToMono(MultipleEntityRecord400ResponseException.Response.class)
                    .flatMap(body -> Mono.error(new MultipleEntityRecord400ResponseException(body)));
        } else {
            return response.bodyToMono(String.class)
                    // defaultIfEmpty:  401's, 403's, etc. sometimes return null body
                    // https://careydevelopment.us/blog/spring-webflux-how-to-handle-empty-responses
                    .defaultIfEmpty(status.getReasonPhrase())
                    .flatMap(body -> {
                        log.info("Error status code {} ({}) Response Body: {}", status.value(), status.getReasonPhrase(), body);
                        // return Mono.just(response); <-- throws WebClient exception back to client instead
                        return Mono.error(new ServiceException(body, response.statusCode().value()));
                    });
        }
    }

}
