package br.com.demo.sfclient.domain.salesforce.command;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCaseDetail extends CompositeEntityRecord {

    public SalesforceCaseDetail(Object body, String referenceId) {
        super("CaseDetail__c", body, Method.POST, referenceId);
    }
    
}
