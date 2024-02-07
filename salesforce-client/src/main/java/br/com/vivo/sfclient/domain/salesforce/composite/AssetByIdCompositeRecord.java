package br.com.vivo.sfclient.domain.salesforce.composite;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class AssetByIdCompositeRecord extends CompositeEntityRecord {
    
    private static final String QUERY = "SELECT Id FROM Asset WHERE vlocity_cmt__ServiceIdentifier__c='%s'";

    public AssetByIdCompositeRecord(String referenceId, String assetId) {
        super("Case", Method.GET, referenceId, String.format(QUERY, assetId));
    }
}
