package br.com.demo.sfclient.domain.salesforce.command;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCase extends CompositeEntityRecord {

    public SalesforceCase(Object body, String referenceId) {
        super("Case", body, Method.POST, referenceId);
    }
    
}
