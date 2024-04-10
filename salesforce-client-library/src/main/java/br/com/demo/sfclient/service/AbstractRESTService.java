package br.com.demo.sfclient.service;

import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRESTService {

    protected WebClient webClient;

    protected final ObjectMapper objectMapper;

    public AbstractRESTService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected AbstractRESTService(WebClient webClient, ObjectMapper objectMapper) {
        this(objectMapper);
        this.webClient = webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    protected JavaType createJavaType(Class<?> clazz) {
        JavaType jt = objectMapper.getTypeFactory().constructType(clazz);
        log.info("Created new JavaType for {}", clazz);
        return jt;
    }
}
