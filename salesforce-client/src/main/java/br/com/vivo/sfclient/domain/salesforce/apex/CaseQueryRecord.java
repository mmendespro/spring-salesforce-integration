package br.com.vivo.sfclient.domain.salesforce.apex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.vivo.sfclient.model.EntityRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseQueryRecord extends EntityRecord {
    
    public static final String LIST_CASE_QUERY = "SELECT Id, AccountId, CaseNumber, Subject, Status, Reason, OmniChannel__c FROM Case";
    public static final String BYACCOUNT_CASE_QUERY = "SELECT Id, AccountId, CaseNumber, Subject, Status, Reason, OmniChannel__c FROM Case WHERE AccountId='%s'";
    public static final String BYID_CASE_QUERY = "SELECT Id, CaseNumber, Subject, Status, Reason, OmniChannel__c, AccountId FROM Case WHERE Id='%s'";

    @JsonProperty(value = "Id")
    private String caseId;

    @JsonProperty(value = "CaseNumber")
    private String caseNumber;

    @JsonProperty(value = "Subject")
    private String caseSubject;

    @JsonProperty(value = "AccountId")
    private String accountId;

    @JsonProperty(value = "Status")
    private String caseStatus;

    @JsonProperty(value = "Reason")
    private String caseReason;

    @JsonProperty(value = "OmniChannel__c")
    private Boolean omniChannel;
}
