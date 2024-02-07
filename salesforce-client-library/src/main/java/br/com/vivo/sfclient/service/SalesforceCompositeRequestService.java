package br.com.vivo.sfclient.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vivo.sfclient.model.auth.SalesforceLoginResult;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordRequest;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConditionalOnProperty(name = "salesforce.client.enabled", matchIfMissing = true)
public class SalesforceCompositeRequestService extends AbstractRESTService {

    private final String API_VERSION = "v56.0";
    private final SalesforceAuthService salesforceAuthService;

    public SalesforceCompositeRequestService(WebClient webClient, ObjectMapper objectMapper, SalesforceAuthService salesforceAuthService) {
        super(webClient, objectMapper);
        this.salesforceAuthService = salesforceAuthService;
    }

    public CompositeEntityRecordResponse bulkProcess(CompositeEntityRecordRequest compositeRequest) throws JsonProcessingException {
        // Auth in Salesforce
        SalesforceLoginResult sfAuthResult = salesforceAuthService.loginSalesforce();

        // create URLs for gets
        compositeRequest.getCompositeRequest()
                        .stream()
                        .filter(r -> CompositeEntityRecord.Method.GET.equals(r.getMethod()))
                        .forEach(ce -> ce.setUrl(String.format("/services/data/%s/query/?q=%s", API_VERSION, ce.getQuery())));
                              
        // create URLs for inserts
        compositeRequest.getCompositeRequest()
            .stream()
            .filter(r -> CompositeEntityRecord.Method.POST.equals(r.getMethod()))
            .forEach(compositeEntity -> compositeEntity.setUrl(String.format("/services/data/%s/sobjects/%s", API_VERSION, compositeEntity.getEntity())));

        // create URLs for updates
        compositeRequest.getCompositeRequest()
            .stream()
            .filter(r -> CompositeEntityRecord.Method.PATCH.equals(r.getMethod()))
            .forEach(compositeEntity -> compositeEntity.setUrl(String.format("/services/data/%s/sobjects/%s/id/%s", API_VERSION, compositeEntity.getEntity(), compositeEntity.getReferenceId())));

        // build json composit request body
        String jsonString = objectMapper.writeValueAsString(compositeRequest);

        log.info("Request for Salesforce Composite API {}", jsonString);
                
        return webClient.post()
                        .uri(sfAuthResult.getInstanceUrl() + "/services/data/" + API_VERSION + "/composite")
                        .headers(h -> h.setBearerAuth(sfAuthResult.getAccessToken()))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(jsonString)
                        .retrieve()
                        .bodyToMono(CompositeEntityRecordResponse.class)
                        // retry of 1: if access token expired, will be removed after
                        // first failed call and obtained & used during second.
                        // Can confirm by revoking token in Salesforce (Setup: Security: Session Management screen)
                        // and comparing results vs. retry of 0
                        .retry(1)
                        .block();
    }

}
