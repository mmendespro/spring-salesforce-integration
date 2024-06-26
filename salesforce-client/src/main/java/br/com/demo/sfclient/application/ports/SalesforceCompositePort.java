package br.com.demo.sfclient.application.ports;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;
import br.com.demo.sfclient.model.composite.CompositeEntityRecordResponse;

public interface SalesforceCompositePort {
    
    public CompositeEntityRecordResponse load(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException;
    public CompositeEntityRecordResponse save(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException;
}
