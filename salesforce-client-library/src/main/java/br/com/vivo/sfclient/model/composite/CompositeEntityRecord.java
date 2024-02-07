package br.com.vivo.sfclient.model.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Support for Composite calls, max of 25 subrequests per call.
 * For bulk inserts where each insert has no relation to the others MultipleEntityRecord is preferred, as it
 * has a 200 max per call.
 */
public abstract class CompositeEntityRecord {

    private final String referenceId;
    private final Method method;
    private String url;

    @JsonInclude(Include.NON_NULL)
    private Object body;
    
    @JsonIgnore
    private final String entity;

    @JsonIgnore
    private final String query;

    public CompositeEntityRecord(String entity, Method method, String referenceId) {
        this.entity = entity;
        this.method = method;
        this.referenceId = referenceId;
        this.query = null;
    }

    public CompositeEntityRecord(String entity, Object body, Method method, String referenceId) {
        this.entity = entity;
        this.method = method;
        this.referenceId = referenceId;
        this.body = body;
        this.query = null;
    }

    public CompositeEntityRecord(String entity, Method method, String referenceId, String query) {
        this.entity = entity;
        this.method = method;
        this.referenceId = referenceId;
        this.query = query;
    }

    // entity represents the type of object the CRUD action applies to (Contact, Account, etc.)
    public String getEntity() {
        return entity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public Method getMethod() {
        return method;
    }

    public enum Method {
        GET,
        PATCH, // updates
        POST; // inserts
    }

    public String getQuery() {
        return query;
    }

    public Object getBody() {
        return body;
    }

}
