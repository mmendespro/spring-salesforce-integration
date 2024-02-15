package br.com.vivo.sfclient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.vivo.sfclient.application.ports.SalesforceCompositePort;
import br.com.vivo.sfclient.application.usecase.CreateChangeAddressCaseUC;
import br.com.vivo.sfclient.application.usecase.CreateOrderTrackingCaseUC;
import br.com.vivo.sfclient.application.usecase.SalesforceUseCaseFactory;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public ObjectMapper createObjectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return JsonMapper.builder()
                // allow "Name" in JSON to map to "name" in class
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                // timestamps to Instant (https://stackoverflow.com/q/45762857/1207540)
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .build()
                .registerModule(timeModule);
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    public CreateOrderTrackingCaseUC createOrderTrackingCaseUC(ObjectMapper mapper, SalesforceCompositePort sfClient) {
        return new CreateOrderTrackingCaseUC(mapper, sfClient);
    }

    @Bean
    public CreateChangeAddressCaseUC createChangeAddressCaseUC(ObjectMapper mapper, SalesforceCompositePort sfClient) {
        return new CreateChangeAddressCaseUC(mapper, sfClient);
    }

    @Bean
    public SalesforceUseCaseFactory salesforceUseCaseFactory(CreateOrderTrackingCaseUC createOrderTrackingCaseUC, CreateChangeAddressCaseUC createChangeAddressCaseUC) {
        return new SalesforceUseCaseFactory(createOrderTrackingCaseUC, createChangeAddressCaseUC);
    }
}
