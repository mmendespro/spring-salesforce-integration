package br.com.vivo.sfclient.filters;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import br.com.vivo.sfclient.exceptions.ServiceException;
import reactor.core.publisher.Mono;

public class WebClientFilter {

    private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logHeaders(request);

            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logStatus(response);
            logHeaders(response);

            return Mono.just(response);
        });
    }

    private static void logStatus(ClientResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.statusCode().value());
        LOG.debug("Returned status code {} ({})", status.value(), status.getReasonPhrase());
    }

    /**
     * Different API calls return different 400-series exception objects, but regardless 401 codes need to be passed through
     * to inform Spring to get a new access token.
     *
     * @param monoCRFunction - the response object to return in case of a non-401 and non-500 error
     * @return
     */
    public static ExchangeFilterFunction handleErrors(Function<ClientResponse, Mono<ClientResponse>> monoCRFunction) {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            HttpStatus status = HttpStatus.valueOf(response.statusCode().value());
            // don't wrap 401 errors, as Spring uses that code to delete the expired access token and request a new one
            if ((status.is4xxClientError() && status.value() != 401) || status.is5xxServerError()) {
                return monoCRFunction.apply(response);
            } else {
                return Mono.just(response);
            }
        });
    }

    public static Mono<Object> getMonoClientResponse(ClientResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.statusCode().value());

        return response.bodyToMono(String.class)
                // defaultIfEmpty:  401's, 403's, etc. sometimes return null body
                // https://careydevelopment.us/blog/spring-webflux-how-to-handle-empty-responses
                .defaultIfEmpty(status.getReasonPhrase())
                .flatMap(body -> {
                    LOG.info("Error status code {} ({}) Response Body: {}", status.value(),
                            status.getReasonPhrase(), body);
                    // return Mono.just(response); <-- throws WebClient exception back to client instead
                    return Mono.error(new ServiceException(body, response.statusCode().value()));
                });
    }

    private static void logHeaders(ClientResponse response) {
        response.headers().asHttpHeaders().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }

    private static void logHeaders(ClientRequest request) {
        request.headers().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }

    private static void logNameAndValuePair(String name, String value) {
        LOG.debug("{}={}", name, value);
    }

    private static void logMethodAndUrl(ClientRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());

        LOG.debug(sb.toString());
    }
}
