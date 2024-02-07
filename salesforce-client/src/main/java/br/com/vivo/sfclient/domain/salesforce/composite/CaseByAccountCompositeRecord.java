package br.com.vivo.sfclient.domain.salesforce.composite;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class CaseByAccountCompositeRecord extends CompositeEntityRecord {
    
    private static final String CASE_QUERY = "SELECT Id, AccountId, CaseNumber, Subject, Status, Reason, OmniChannel__c FROM Case WHERE AccountId='%s'";

    public CaseByAccountCompositeRecord(String referenceId, String accountId) {
        super("Case", Method.GET, referenceId, String.format(CASE_QUERY, accountId));
    }
}
