package br.com.demo.sfclient.application.usecase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.demo.sfclient.application.dto.SalesforceRequest;
import br.com.demo.sfclient.application.ports.SalesforceCompositePort;
import br.com.demo.sfclient.domain.myrequest.OrderTrackingCase;
import br.com.demo.sfclient.domain.salesforce.command.SalesforceCase;
import br.com.demo.sfclient.domain.salesforce.command.SalesforceCaseDetail;
import br.com.demo.sfclient.domain.salesforce.command.SalesforceCustomerInteraction;
import br.com.demo.sfclient.domain.salesforce.command.SalesforceCustomerInteractionTopic;
import br.com.demo.sfclient.domain.salesforce.query.AccountRef;
import br.com.demo.sfclient.domain.salesforce.query.AssetRef;
import br.com.demo.sfclient.domain.salesforce.query.RecordTypeRef;
import br.com.demo.sfclient.model.composite.CompositeEntityRecordResponse;

public class CreateOrderTrackingCaseUC extends BaseUseCase<OrderTrackingCase>{
    
    public CreateOrderTrackingCaseUC(ObjectMapper mapper, SalesforceCompositePort sfClient) {
        super(mapper, sfClient);
    }

    @Override
    public CompositeEntityRecordResponse handle(SalesforceRequest request) throws JsonProcessingException {
        // convert request on domain object
        var sfUserCase = mapToDomain(request, OrderTrackingCase.class).orElseThrow(() -> {throw new RuntimeException("Invalid payload converter");});

        // Phases to create a case
        // -> Find the customer
        var sfCustReq = new AccountRef("AccountReturnedRef", sfUserCase.getCustomerId());
        
        // -> Find the Asset
        var sfAssetReq = new AssetRef("AssetRef", sfUserCase.getProductId());

        // -> Find the recordType for case
        var sfRecordTypeCaseReq = new RecordTypeRef("CaseRecTypeRef", "HelpWithTheOrder", true);        

        // -> Find the recordType for case detail
        var sfRecordTypeCaseDetailReq = new RecordTypeRef("CaseDetailRecTypeRef", "HelpWithTheOrder", false);   
        
        // -> Create the case
        var sfCaseProps = new HashMap<>();
            sfCaseProps.put("RecordTypeId", "@{CaseRecTypeRef.records[0].Id}");
            sfCaseProps.put("AccountId", "@{AccountReturnedRef.records[0].Id}");
            sfCaseProps.put("ContactId", "@{AccountReturnedRef.records[0].vlocity_cmt__PrimaryContactId__c}");
            sfCaseProps.put("ComplainedAsset__c", "@{AssetRef.records[0].Id}");
            sfCaseProps.put("Protocol__c", sfUserCase.getProtocolNumber());
            sfCaseProps.put("Origin", "APP");
            sfCaseProps.put("Omnichannel__c", "true");

        var sfCase = new SalesforceCase(sfCaseProps, "NewCase");

        // -> Create case details
        var sfCaseDetailProps = new HashMap<>();
            sfCaseDetailProps.put("RecordTypeId", "@{CaseDetailRecTypeRef.records[0].Id}");
            sfCaseDetailProps.put("Case__c", "@{NewCase.id}");
            sfCaseDetailProps.put("OrderNumber__c", sfUserCase.getOrderId());

        var sfCaseDetail = new SalesforceCaseDetail(sfCaseDetailProps, "NewCaseDetails");
        
        // -> Create the interaction
        var sfCustIntProps = new LinkedHashMap<>();
            sfCustIntProps.put("name", "@{AccountReturnedRef.records[0].Name}");
            sfCustIntProps.put("vlocity_cmt__AccountId__c", "@{AccountReturnedRef.records[0].Id}");
            sfCustIntProps.put("vlocity_cmt__ContactId__c", "@{AccountReturnedRef.records[0].vlocity_cmt__PrimaryContactId__c}");
            sfCustIntProps.put("vlocity_cmt__Status__c", "In Progress");
            sfCustIntProps.put("InteractionNumber__c", sfUserCase.getProtocolNumber());
            sfCustIntProps.put("IdentifierNumber__c", sfUserCase.getProductId());
            sfCustIntProps.put("ExternalId__c", "011CRLK71GADTBTBEEO822LAES000052");
            sfCustIntProps.put("vlocity_cmt__Type__c", "APP Vivo");
            sfCustIntProps.put("Origin__c", "APP Vivo");
            sfCustIntProps.put("Subject__c", "Ajuda com pedido");

        var sfCustInt = new SalesforceCustomerInteraction(sfCustIntProps, "NewCustomerInteraction");

        // -> Create the interation Topic
        var sfCustIntTopicProps = new LinkedHashMap<>();
            sfCustIntTopicProps.put("vlocity_cmt__CaseId__c", "@{NewCase.id}");
            sfCustIntTopicProps.put("vlocity_cmt__CustomerInteractionId__c", "@{NewCustomerInteraction.id}");

        var sfIntTopic = new SalesforceCustomerInteractionTopic(sfCustIntTopicProps, "NewCustomerInteractionTopic");

        // Create and return
        return sfClient.save(List.of(sfCustReq, sfAssetReq, sfRecordTypeCaseReq, sfRecordTypeCaseDetailReq, sfCase, sfCaseDetail, sfCustInt, sfIntTopic));
    }
}
