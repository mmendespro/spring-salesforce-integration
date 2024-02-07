package br.com.vivo.sfclient.domain.salesforce.composite;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class GetNewCaseCompositeRecord extends CompositeEntityRecord {
    
    private static final String QUERY = "SELECT Id FROM Case WHERE Id='@{NewCaseRef.id}'";

    public GetNewCaseCompositeRecord(String referenceId) {
        super("Case", Method.GET, referenceId, QUERY);
    }
}
