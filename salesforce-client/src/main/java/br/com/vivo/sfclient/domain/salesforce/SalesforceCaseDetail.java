package br.com.vivo.sfclient.domain.salesforce;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCaseDetail extends CompositeEntityRecord {

    public SalesforceCaseDetail(Object body, String referenceId) {
        super("CaseDetail__c", body, Method.POST, referenceId);
    }
    
}
