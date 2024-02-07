package br.com.vivo.sfclient.domain.salesforce.composite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCaseCompositeRecord extends CompositeEntityRecord {
    
    public CreateCaseCompositeRecord(String referenceId, Object body) {
        super("Case", body, Method.POST, referenceId);
    }    
}
