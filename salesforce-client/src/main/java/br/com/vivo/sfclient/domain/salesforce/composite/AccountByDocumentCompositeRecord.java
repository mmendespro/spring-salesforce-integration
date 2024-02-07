package br.com.vivo.sfclient.domain.salesforce.composite;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class AccountByDocumentCompositeRecord extends CompositeEntityRecord {

    private static final String QUERY = "SELECT Id, name, vlocity_cmt__PrimaryContactId__c FROM Account WHERE DocumentNumber__c ='%s'";

    public AccountByDocumentCompositeRecord(String referenceId, String documentNumber) {
        super("Account", Method.GET, referenceId, String.format(QUERY, documentNumber));
    }
    
}
