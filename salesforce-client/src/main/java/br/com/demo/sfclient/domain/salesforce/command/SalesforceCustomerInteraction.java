package br.com.demo.sfclient.domain.salesforce.command;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCustomerInteraction extends CompositeEntityRecord {
    
    public SalesforceCustomerInteraction(Object body, String referenceId) {
        super("vlocity_cmt__CustomerInteraction__c", body, Method.POST, referenceId);
    }
}
