package br.com.demo.sfclient.domain.salesforce.query;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class AccountRef extends  CompositeEntityRecord {

    private static final String QUERY = "SELECT Id, name, vlocity_cmt__PrimaryContactId__c FROM Account WHERE DocumentNumber__c='%s'";

    public AccountRef(String referenceId, String accountId) {
        super("Account", Method.GET, referenceId, String.format(QUERY, accountId));
    }
    
}
