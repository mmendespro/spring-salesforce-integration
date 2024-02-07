package br.com.vivo.sfclient.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.vivo.sfclient.configuration.EnableSfClientConfig;
import br.com.vivo.sfclient.model.auth.SalesforceLoginResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConditionalOnProperty(name = "salesforce.client.enabled", matchIfMissing = true)
public class SalesforceAuthService {
    
    private final WebClient webClient;
    private final EnableSfClientConfig sfClientConfig;
    private static final String TOKEN_URL =  "https://valentinatelefonica--devmisoli.sandbox.my.salesforce.com/services/oauth2/token";

    public SalesforceAuthService(WebClient webClient, EnableSfClientConfig sfClientConfig) {
        this.webClient = webClient;
        this.sfClientConfig = sfClientConfig;
    }

    public SalesforceLoginResult loginSalesforce() {
        log.info("Get Salesforce Authentication");
        return webClient.post()
                        .uri(TOKEN_URL)
                        .body(
                            BodyInserters.fromFormData("grant_type", "password")
                                            .with("username", sfClientConfig.getUsername())
                                            .with("password", sfClientConfig.getPassword())
                                            .with("client_id", sfClientConfig.getConsumerKey())
                                            .with("client_secret", sfClientConfig.getConsumerSecret())
                        )
                        .retrieve()
                        .bodyToMono(SalesforceLoginResult.class)
                        .block();
    }
}
