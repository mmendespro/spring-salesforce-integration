package br.com.vivo.sfclient.domain.salesforce.query;

import br.com.vivo.sfclient.model.composite.CompositeEntityRecord;

public class RecordTypeRef extends CompositeEntityRecord {

    private static final String QUERY_CASE = "SELECT Id FROM RecordType WHERE DeveloperName='%s' AND SobjectType='Case'";
    private static final String QUERY_CASE_DETAIL = "SELECT Id FROM RecordType WHERE DeveloperName='%s' AND SobjectType='CaseDetail__c'";

    public RecordTypeRef(String referenceId, String recordTypeId, boolean isCase) {
        super("RecordType", Method.GET, referenceId, String.format(isCase ? QUERY_CASE : QUERY_CASE_DETAIL, recordTypeId));
    }
}