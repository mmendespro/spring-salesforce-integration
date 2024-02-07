package br.com.vivo.sfclient.application.usecase.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.domain.ports.composite.SalesforceCompositeCasePort;
import br.com.vivo.sfclient.domain.salesforce.composite.AccountByDocumentCompositeRecord;
import br.com.vivo.sfclient.domain.salesforce.composite.AssetByIdCompositeRecord;
import br.com.vivo.sfclient.domain.salesforce.composite.CaseDetailCompositeRecord;
import br.com.vivo.sfclient.domain.salesforce.composite.CreateCaseCompositeRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;

public class CreateCaseCompositeUC {

    private final SalesforceCompositeCasePort salesforceCompositeCasePort;

    public CreateCaseCompositeUC(SalesforceCompositeCasePort salesforceCompositeCasePort) {
        this.salesforceCompositeCasePort = salesforceCompositeCasePort;
    }

    public CompositeEntityRecordResponse handle(SalesforceRequest request) throws JsonProcessingException {
        AccountByDocumentCompositeRecord accountRef = new AccountByDocumentCompositeRecord("AccountReturnedRef", "74674326230"); 
        AssetByIdCompositeRecord assetRef = new AssetByIdCompositeRecord("AssetRef", "0987654321");
        CreateCaseCompositeRecord newCase = this.createCase(request);
        //GetNewCaseCompositeRecord newCaseGet = new GetNewCaseCompositeRecord("GetNewCase");
        CaseDetailCompositeRecord newCaseDetail = createCaseDetail(request);

        // monta a lista de composite request
        List<? extends CompositeEntityRecord> compositeRequest = new ArrayList<>(List.of(accountRef, assetRef, newCase, newCaseDetail));

        // chama a api composite
        CompositeEntityRecordResponse response = salesforceCompositeCasePort.save(compositeRequest);

        return response;
    }

    private CreateCaseCompositeRecord createCase(SalesforceRequest request) {
        Map<String, Object> props = new HashMap<>();
        props.put("AccountId", "@{AccountReturnedRef.records[0].Id}");
        props.put("ContactId", "@{AccountReturnedRef.records[0].vlocity_cmt__PrimaryContactId__c}");
        props.put("ComplainedAsset__c", "@{AssetRef.records[0].Id}");
        props.put("Protocol__c", "123456789");
        props.put("OmniChannel__c", request.get("OmniChannel__c"));
        props.put("Origin", request.get("Origin"));
        props.put("RecordType",  Map.of("Name", "Minhas Solicitações"));
        return new CreateCaseCompositeRecord("NewCaseRef", props);
    }

    private CaseDetailCompositeRecord createCaseDetail(SalesforceRequest request) {
        Map<String, Object> props = new HashMap<>();
        props.put("RecordType",  Map.of("Name", "Mudança de Endereço"));
        props.put("Case__c", "@{NewCaseRef.id}");
        return new CaseDetailCompositeRecord("NewCaseDetails", props);
    }
}
