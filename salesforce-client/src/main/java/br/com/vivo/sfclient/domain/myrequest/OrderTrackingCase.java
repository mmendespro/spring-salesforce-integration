package br.com.vivo.sfclient.domain.myrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTrackingCase extends BaseCase {

    private final String orderId;

    public OrderTrackingCase() {
        super(null, null, null, null, SalesforceReason.TRACKING_PROBLEM);
        this.orderId = null;
    }

    public OrderTrackingCase(String customerId, String productId, String protocolNumber, SalesforceStatus status, String orderId) {
        super(customerId, productId, protocolNumber, status, SalesforceReason.TRACKING_PROBLEM);
        this.orderId = orderId;
    }
    
    public static OrderTrackingCase openCase(String customerId, String productId, String protocolNumber, String orderId) {
        return  new OrderTrackingCase(customerId, productId, protocolNumber, SalesforceStatus.IN_PROGRESS, orderId);
    }

    public static OrderTrackingCase closeCase(String customerId, String productId, String protocolNumber, String orderId) {
        return new OrderTrackingCase(customerId, productId, protocolNumber, SalesforceStatus.CANCELLED, orderId);
    }

    public String getOrderId() {
        return orderId;
    }
}
