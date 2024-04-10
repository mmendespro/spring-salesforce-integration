package br.com.demo.sfclient.adapters.client;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.demo.sfclient.application.ports.SalesforceCompositePort;
import br.com.demo.sfclient.model.composite.CompositeEntityRecord;
import br.com.demo.sfclient.model.composite.CompositeEntityRecordRequest;
import br.com.demo.sfclient.model.composite.CompositeEntityRecordResponse;
import br.com.demo.sfclient.service.SalesforceCompositeRequestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesforceCompositeClient implements SalesforceCompositePort {
    
    private final SalesforceCompositeRequestService salesforceCompositeRequestService;

    public SalesforceCompositeClient(SalesforceCompositeRequestService salesforceCompositeRequestService) {
        this.salesforceCompositeRequestService = salesforceCompositeRequestService;
    }

    @Override
    public CompositeEntityRecordResponse load(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException {
        log.info("Request to get Salesforce Cases");
        
        CompositeEntityRecordRequest compsiteRequest = new CompositeEntityRecordRequest(true);
        compsiteRequest.setCompositeRequest(compositeRequest);

        CompositeEntityRecordResponse compositeResponse = salesforceCompositeRequestService.bulkProcess(compsiteRequest);
        return compositeResponse;
    }

    @Override
    public CompositeEntityRecordResponse save(List<? extends CompositeEntityRecord> compositeRequest) throws JsonProcessingException {
        log.info("Request to save Salesforce Cases");
        CompositeEntityRecordRequest compsiteRequest = new CompositeEntityRecordRequest(true);
        compsiteRequest.setCompositeRequest(compositeRequest);

        CompositeEntityRecordResponse compositeResponse = salesforceCompositeRequestService.bulkProcess(compsiteRequest);
        return compositeResponse;
    }

}
