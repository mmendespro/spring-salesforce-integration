package br.com.vivo.sfclient.application.usecase;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.domain.ports.SalesforceCompositePort;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;

public abstract class BaseUseCase<T> {
    
    protected final ObjectMapper mapper;
    protected final SalesforceCompositePort sfClient;
    
    public BaseUseCase(ObjectMapper mapper, SalesforceCompositePort sfClient) {
        this.mapper = mapper;
        this.sfClient = sfClient;
    }
    
    protected abstract Optional<T> mapToDomain(Map<?,?> input);
    
    public abstract CompositeEntityRecordResponse handle(SalesforceRequest request) throws JsonProcessingException;
}
