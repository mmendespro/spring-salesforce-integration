package br.com.vivo.sfclient.domain.salesforce;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class SalesforceCase extends CompositeEntityRecord {

    public SalesforceCase(Object body, String referenceId) {
        super("Case", body, Method.POST, referenceId);
    }
    
}
