package br.com.demo.sfclient.domain.salesforce.query;

import br.com.demo.sfclient.model.composite.CompositeEntityRecord;

public class AssetRef extends CompositeEntityRecord {

    private static final String QUERY = "SELECT Id FROM Asset WHERE vlocity_cmt__ServiceIdentifier__c='%s'";

    public AssetRef(String referenceId, String assetId) {
        super("Asset", Method.GET, referenceId, String.format(QUERY, assetId));
    }
}
