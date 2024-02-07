package br.com.vivo.sfclient.adapters.client;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.domain.ports.apex.SalesforceApexCasePort;
import br.com.vivo.sfclient.domain.salesforce.apex.CaseQueryRecord;
import br.com.vivo.sfclient.model.SOQLQueryResponse;
import br.com.vivo.sfclient.model.composite.RecordCreateResponse;
import br.com.vivo.sfclient.service.SOQLQueryRunner;
import br.com.vivo.sfclient.service.SalesforceRecordManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesforceApexClient implements SalesforceApexCasePort {

    private final SOQLQueryRunner soqlQueryRunner;
    private final SalesforceRecordManager salesforceRecordManager;
    
    public SalesforceApexClient(SOQLQueryRunner soqlQueryRunner, SalesforceRecordManager salesforceRecordManager) {
        this.soqlQueryRunner = soqlQueryRunner;
        this.salesforceRecordManager = salesforceRecordManager;
    }

    @Override
    public SOQLQueryResponse<CaseQueryRecord> load() throws JsonProcessingException {
        log.info("request to load Salesforce Case By Query {}", CaseQueryRecord.LIST_CASE_QUERY);
        return soqlQueryRunner.runObjectQuery(CaseQueryRecord.LIST_CASE_QUERY, CaseQueryRecord.class);
    }

    @Override
    public SOQLQueryResponse<CaseQueryRecord> load(String caseId) throws JsonProcessingException {
        log.info("request to load Salesforce Case By Id {}", String.format(CaseQueryRecord.BYID_CASE_QUERY, caseId));
        return soqlQueryRunner.runObjectQuery(String.format(CaseQueryRecord.BYID_CASE_QUERY, caseId), CaseQueryRecord.class);
    }

    @Override
    public SOQLQueryResponse<CaseQueryRecord> loadByAccount(String accountId) throws JsonProcessingException {
        log.info("request to list Salesforce Case By Query AccountId {}", String.format(CaseQueryRecord.BYACCOUNT_CASE_QUERY, accountId));
        return soqlQueryRunner.runObjectQuery(String.format(CaseQueryRecord.BYACCOUNT_CASE_QUERY, accountId), CaseQueryRecord.class);
    }

    @Override
    public RecordCreateResponse save(SalesforceRequest request) throws JsonProcessingException {
        log.info("request to create new Salesforce Case {}", request);
        var result = salesforceRecordManager.create("Case", request);
        return result;
    }
    
}
