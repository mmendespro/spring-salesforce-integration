package br.com.vivo.sfclient.domain.myrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.vivo.sfclient.domain.myrequest.valueObjects.Address;
import br.com.vivo.sfclient.domain.myrequest.valueObjects.TechnicianVisit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeAddressCase extends BaseCase {

    private final Address instalationAddress;
    private final TechnicianVisit technicianVisitOpt1;
    private final TechnicianVisit technicianVisitOpt2;

    public ChangeAddressCase() {
        super(null, null, null, SalesforceStatus.IN_PROGRESS, SalesforceReason.CHANGE_ADDRESS);
        this.instalationAddress = null;
        this.technicianVisitOpt1 = null;
        this.technicianVisitOpt2 = null;
    }

    public ChangeAddressCase(String customerId, String productId, String protocolNumber, SalesforceStatus status, Address instalationAddress, TechnicianVisit technicianVisitOpt1, TechnicianVisit technicianVisitOpt2) {
        super(customerId, productId, protocolNumber, status, SalesforceReason.CHANGE_ADDRESS);
        this.instalationAddress = instalationAddress;
        this.technicianVisitOpt1 = technicianVisitOpt1;
        this.technicianVisitOpt2 = technicianVisitOpt2;
    }
    
    public static ChangeAddressCase openCase(String customerId, String productId, String protocolNumber, Address instalationAddress, TechnicianVisit technicianVisitOpt1, TechnicianVisit technicianVisitOpt2) {
        return  new ChangeAddressCase(customerId, productId, protocolNumber, SalesforceStatus.IN_PROGRESS, instalationAddress, technicianVisitOpt1, technicianVisitOpt2);
    }

    public static ChangeAddressCase closeCase(String customerId, String productId, String protocolNumber, Address instalationAddress, TechnicianVisit technicianVisitOpt1, TechnicianVisit technicianVisitOpt2) {
        return  new ChangeAddressCase(customerId, productId, protocolNumber, SalesforceStatus.CANCELLED, instalationAddress, technicianVisitOpt1, technicianVisitOpt2);
    }

    public Address getInstalationAddress() {
        return instalationAddress;
    }

    public TechnicianVisit getTechnicianVisitOpt1() {
        return technicianVisitOpt1;
    }

    public TechnicianVisit getTechnicianVisitOpt2() {
        return technicianVisitOpt2;
    }
}
