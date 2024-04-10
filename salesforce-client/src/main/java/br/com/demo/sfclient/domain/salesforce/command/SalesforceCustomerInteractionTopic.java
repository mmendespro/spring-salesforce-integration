package br.com.demo.sfclient.domain.salesforce.command;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCustomerInteractionTopic extends CompositeEntityRecord {
    
    public SalesforceCustomerInteractionTopic(Object body, String referenceId) {
        super("vlocity_cmt__CustomerInteractionTopic__c", body, Method.POST, referenceId);
    }
}