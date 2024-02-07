package br.com.vivo.sfclient.configuration;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration("salesforceConfigurationProperties")
@ConfigurationProperties(prefix = "salesforce")
public class EnableSfClientConfig {
    
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String consumerKey;

    @NotNull
    private String consumerSecret;    
}
