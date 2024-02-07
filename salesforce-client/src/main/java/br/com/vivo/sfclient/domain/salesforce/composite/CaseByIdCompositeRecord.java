package br.com.vivo.sfclient.domain.salesforce.composite;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class CaseByIdCompositeRecord extends CompositeEntityRecord {
    
    private static final String CASE_QUERY = "SELECT Id, AccountId, CaseNumber, Subject, Status, Reason, OmniChannel__c FROM Case WHERE Id='%s'";

    public CaseByIdCompositeRecord(String referenceId, String caseId) {
        super("Case", Method.GET, referenceId, String.format(CASE_QUERY, caseId));
    }
}
