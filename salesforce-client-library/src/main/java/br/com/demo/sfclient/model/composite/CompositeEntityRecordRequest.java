package br.com.demo.sfclient.model.composite;

import java.util.List;

public class CompositeEntityRecordRequest {

    boolean allOrNone;
    List<? extends CompositeEntityRecord> compositeRequest;

    // allOrNone: if true, error in one causes rollback of all.
    // requiring in constructor as it is an important property
    public CompositeEntityRecordRequest(boolean allOrNone) {
        this.allOrNone = allOrNone;
    }

    public List<? extends CompositeEntityRecord> getCompositeRequest() {
        return compositeRequest;
    }

    public void setCompositeRequest(List<? extends CompositeEntityRecord> compositeRequest) {
        this.compositeRequest = compositeRequest;
    }

    public boolean isAllOrNone() {
        return allOrNone;
    }

}
