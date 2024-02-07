package br.com.vivo.sfclient.domain.salesforce.composite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDetailCompositeRecord extends CompositeEntityRecord {
    
    public CaseDetailCompositeRecord(String referenceId, Object body) {
        super("CaseDetail__c", body, Method.POST, referenceId);
    }    
}
