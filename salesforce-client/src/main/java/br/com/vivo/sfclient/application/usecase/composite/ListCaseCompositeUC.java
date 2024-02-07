package br.com.vivo.sfclient.application.usecase.composite;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.adapters.client.SalesforceCompositeClient;

public class ListCaseCompositeUC {
    
    private final SalesforceCompositeClient salesforceCompositeClient;

    public ListCaseCompositeUC(SalesforceCompositeClient salesforceCompositeClient) {
        this.salesforceCompositeClient = salesforceCompositeClient;
    }

    public Map<String, Object> handle(String sfAccountId) throws JsonProcessingException {
        if(!StringUtils.hasText(sfAccountId)) {
            throw new RuntimeException("You must provide an AccountId");
        }
        return salesforceCompositeClient.loadByAccount(sfAccountId).getCompositeResponse().get(0).getSuccessResultsMap();
    }
}
