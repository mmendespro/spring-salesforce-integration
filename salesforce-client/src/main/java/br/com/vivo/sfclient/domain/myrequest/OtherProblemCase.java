package br.com.vivo.sfclient.domain.myrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherProblemCase extends BaseCase {

    private final String problemDescription;

    public OtherProblemCase() {
        super(null, null, null, null, null);
        this.problemDescription = null;
    }

    public OtherProblemCase(String customerId, String productId, String protocolNumber, SalesforceStatus status, String problemDescription) {
        super(customerId, productId, protocolNumber, status, SalesforceReason.OTHER_PROBLEM);
        this.problemDescription = problemDescription;
    }

    public static OtherProblemCase openCase(String customerId, String productId, String protocolNumber, String problemDescription) {
        return  new OtherProblemCase(customerId, productId, protocolNumber, SalesforceStatus.OPEN, problemDescription);
    }

    public static OtherProblemCase closeCase(String customerId, String productId, String protocolNumber, String problemDescription) {
        return new OtherProblemCase(customerId, productId, protocolNumber, SalesforceStatus.CLOSED, problemDescription);
    }    

    public String getProblemDescription() {
        return problemDescription;
    }
}
