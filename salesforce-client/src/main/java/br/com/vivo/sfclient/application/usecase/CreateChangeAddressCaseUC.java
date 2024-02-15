package br.com.vivo.sfclient.application.usecase;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.application.ports.SalesforceCompositePort;
import br.com.vivo.sfclient.domain.myrequest.ChangeAddressCase;
import br.com.vivo.sfclient.domain.salesforce.AccountRef;
import br.com.vivo.sfclient.domain.salesforce.AssetRef;
import br.com.vivo.sfclient.domain.salesforce.SalesforceCase;
import br.com.vivo.sfclient.domain.salesforce.SalesforceCaseDetail;
import br.com.vivo.sfclient.domain.salesforce.valueObjects.RecordType;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;

public class CreateChangeAddressCaseUC extends BaseUseCase<ChangeAddressCase>{
    
    public CreateChangeAddressCaseUC(ObjectMapper mapper, SalesforceCompositePort sfClient) {
        super(mapper, sfClient);
    }

    public CompositeEntityRecordResponse handle(SalesforceRequest request) throws JsonProcessingException {
        // convert request on domain object
        var sfUserCase = mapToDomain(request, ChangeAddressCase.class).orElseThrow(() -> {throw new RuntimeException("Invalid payload converter");});

        // Phases to create a case
        // -> Find the customer
        var sfCustReq = new AccountRef("AccountReturnedRef", sfUserCase.getCustomerId());
        
        // -> Find the Asset
        var sfAssetReq = new AssetRef("AssetRef", sfUserCase.getProductId());
        
        // -> Create the case
        var sfCaseProps = new HashMap<>();
            sfCaseProps.put("RecordType", new RecordType("Minhas Solicitações"));
            sfCaseProps.put("AccountId", "@{AccountReturnedRef.records[0].Id}");
            sfCaseProps.put("ContactId", "@{AccountReturnedRef.records[0].vlocity_cmt__PrimaryContactId__c}");
            sfCaseProps.put("ComplainedAsset__c", "@{AssetRef.records[0].Id}");
            sfCaseProps.put("Protocol__c", sfUserCase.getProtocolNumber());
            sfCaseProps.put("Origin", "APP Vivo");
            sfCaseProps.put("Omnichannel__c", "true");

        var sfCase = new SalesforceCase(sfCaseProps, "NewCase");

        // -> Create case details
        var sfCaseDetailProps = new HashMap<>();
            sfCaseDetailProps.put("RecordType", new RecordType("Mudança de Endereço"));
            sfCaseDetailProps.put("Case__c", "@{NewCase.id}");
            //sfCaseDetailProps.put("Protocol__c", sfUserCase.getProtocolNumber());
            sfCaseDetailProps.put("Street__c", sfUserCase.getInstalationAddress().getStreetAddress());
            sfCaseDetailProps.put("CEP__c", sfUserCase.getInstalationAddress().getPostalCode());
            sfCaseDetailProps.put("City__c", sfUserCase.getInstalationAddress().getLocality());
            sfCaseDetailProps.put("Neighborhood__c", sfUserCase.getInstalationAddress().getCountry());
            sfCaseDetailProps.put("DateOption1__c", sfUserCase.getTechnicianVisitOpt1().getDateOption());
            sfCaseDetailProps.put("PeriodOption1__c",  sfUserCase.getTechnicianVisitOpt1().getPeriod().getDescription());
            sfCaseDetailProps.put("DateOption2__c", sfUserCase.getTechnicianVisitOpt1().getDateOption());
            sfCaseDetailProps.put("PeriodOption2__c",  sfUserCase.getTechnicianVisitOpt1().getPeriod().getDescription());
            
        var sfCaseDetail = new SalesforceCaseDetail(sfCaseDetailProps, "NewCaseDetails");
        
        // -> Create the interaction
        /* 
        var sfCustIntProps = new HashMap<>();
            sfCustIntProps.put("name", "@{AccountReturnedRef.records[0].Name}");
            sfCustIntProps.put("vlocity_cmt__AccountId__c", "@{AccountReturnedRef.records[0].Id}");
            sfCustIntProps.put("vlocity_cmt__ContactId__c", "@{AccountReturnedRef.records[0].vlocity_cmt__PrimaryContactId__c}");
            sfCustIntProps.put("vlocity_cmt__Status__c", "Completed");
            sfCustIntProps.put("InteractionNumber__c", sfUserCase.getProtocolNumber());
            sfCustIntProps.put("IdentifierNumber__c", "4130863031");
            sfCustIntProps.put("ExternalId__c", "011CRLK71GADTBTBEEO822LAES000052");
            sfCustIntProps.put("vlocity_cmt__Type__c", "Mobile App");
            sfCustIntProps.put("Origin__c", "APP Vivo");
            sfCustIntProps.put("Subject__c", sfUserCase.getReason().name());

        var sfCustInt = new SalesforceCustomerInteraction(sfCustIntProps, "NewCustomerInteraction");
        */
        
        // Create and return
        return sfClient.save(List.of(sfCustReq, sfAssetReq, sfCase, sfCaseDetail));
    }
}
