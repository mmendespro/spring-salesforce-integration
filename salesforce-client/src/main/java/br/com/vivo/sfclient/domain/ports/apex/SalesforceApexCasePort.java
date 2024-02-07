package br.com.vivo.sfclient.domain.ports.apex;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.domain.salesforce.apex.CaseQueryRecord;
import br.com.vivo.sfclient.model.SOQLQueryResponse;
import br.com.vivo.sfclient.model.composite.RecordCreateResponse;

public interface SalesforceApexCasePort {
    public SOQLQueryResponse<CaseQueryRecord> load() throws JsonProcessingException;
    public SOQLQueryResponse<CaseQueryRecord> load(String caseId) throws JsonProcessingException;
    public SOQLQueryResponse<CaseQueryRecord> loadByAccount(String accountId) throws JsonProcessingException;

    public RecordCreateResponse save(SalesforceRequest request) throws JsonProcessingException;
}
