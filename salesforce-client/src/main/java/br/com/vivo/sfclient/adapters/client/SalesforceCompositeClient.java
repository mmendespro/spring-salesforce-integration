package br.com.vivo.sfclient.adapters.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.domain.ports.composite.SalesforceCompositeCasePort;
import br.com.vivo.sfclient.domain.salesforce.composite.CaseByAccountCompositeRecord;
import br.com.vivo.sfclient.domain.salesforce.composite.CaseByIdCompositeRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordRequest;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;
import br.com.vivo.sfclient.service.SalesforceCompositeRequestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesforceCompositeClient implements SalesforceCompositeCasePort {
    
    private final SalesforceCompositeRequestService salesforceCompositeRequestService;

    public SalesforceCompositeClient(SalesforceCompositeRequestService salesforceCompositeRequestService) {
        this.salesforceCompositeRequestService = salesforceCompositeRequestService;
    }

    @Override
    public CompositeEntityRecordResponse loadByCaseId(String caseId) throws JsonProcessingException {
        log.info("request to get Salesforce Case By CaseId {}", caseId);
        CaseByIdCompositeRecord compositeRecord = new CaseByIdCompositeRecord("CaseByIdRef", caseId);
        
        CompositeEntityRecordRequest compsiteRequest = new CompositeEntityRecordRequest(true);
        compsiteRequest.setCompositeRequest(new ArrayList<>(List.of(compositeRecord)));

        CompositeEntityRecordResponse compositeResponse = salesforceCompositeRequestService.bulkProcess(compsiteRequest);
        return compositeResponse;
    }

    @Override
    public CompositeEntityRecordResponse loadByAccount(String accountId) throws JsonProcessingException {
        log.info("request to list Salesforce Cases By Account {}", accountId);
        CaseByAccountCompositeRecord compositeRecord = new CaseByAccountCompositeRecord("CasesByAccountRef", accountId);

        CompositeEntityRecordRequest compsiteRequest = new CompositeEntityRecordRequest(true);
        compsiteRequest.setCompositeRequest(new ArrayList<>(List.of(compositeRecord)));

        CompositeEntityRecordResponse compositeResponse = salesforceCompositeRequestService.bulkProcess(compsiteRequest);
        return compositeResponse;
    }

    @Override
    public CompositeEntityRecordResponse save(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException {
        CompositeEntityRecordRequest compsiteRequest = new CompositeEntityRecordRequest(true);
        compsiteRequest.setCompositeRequest(compositeRequest);

        CompositeEntityRecordResponse compositeResponse = salesforceCompositeRequestService.bulkProcess(compsiteRequest);
        return compositeResponse;
    }

}
