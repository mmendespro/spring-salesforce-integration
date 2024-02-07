package br.com.vivo.sfclient.application.usecase.apex;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.application.dto.SalesforceResponse;
import br.com.vivo.sfclient.domain.ports.apex.SalesforceApexCasePort;

public class CreateCaseApexUC {
    
    private final SalesforceApexCasePort salesforceApexCasePort;

    public CreateCaseApexUC(SalesforceApexCasePort salesforceApexCasePort) {
        this.salesforceApexCasePort = salesforceApexCasePort;
    }

    public SalesforceResponse handle(SalesforceRequest request) throws JsonProcessingException {
        var result = salesforceApexCasePort.save(request);
        request.put("CaseId", result.getId());

        var response = new SalesforceResponse();
        //response.putAll(request);
        response.put("CaseId", result.getId());

        return response;
    }
}
