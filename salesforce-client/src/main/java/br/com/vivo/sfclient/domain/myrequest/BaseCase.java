package br.com.vivo.sfclient.domain.myrequest;

public abstract class BaseCase {
    
    private final String customerId;
    private final String productId;
    private final String protocolNumber;
    private final SalesforceStatus status;
    private final SalesforceReason reason;

    public BaseCase(String customerId, String productId, String protocolNumber, SalesforceStatus status, SalesforceReason reason) {
        this.customerId = customerId;
        this.productId = productId;
        this.protocolNumber = protocolNumber;
        this.status = status;
        this.reason = reason;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public SalesforceStatus getStatus() {
        return status;
    }

    public SalesforceReason getReason() {
        return reason;
    }

    public enum SalesforceStatus {
        WAITING_ANSWER,
        IN_PROGRESS,
        EXPIRED,
        CANCELLED,
        COMPLETED;
    }

    public enum SalesforceReason {
        CHANGE_ADDRESS,
        TRACKING_PROBLEM,
        OTHER_PROBLEM;
    }
}
