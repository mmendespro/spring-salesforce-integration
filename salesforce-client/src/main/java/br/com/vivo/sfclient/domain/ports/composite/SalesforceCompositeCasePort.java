package br.com.vivo.sfclient.domain.ports.composite;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;

public interface SalesforceCompositeCasePort {
    
    public CompositeEntityRecordResponse loadByCaseId(String caseId) throws JsonProcessingException;
    public CompositeEntityRecordResponse loadByAccount(String accountId) throws JsonProcessingException;

    public CompositeEntityRecordResponse save(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException;
}
