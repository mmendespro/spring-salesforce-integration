package br.com.vivo.sfclient.application.usecase.apex;

import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.CaseResponse;
import br.com.vivo.sfclient.domain.ports.apex.SalesforceApexCasePort;
import br.com.vivo.sfclient.domain.salesforce.apex.CaseQueryRecord;
import br.com.vivo.sfclient.model.SOQLQueryResponse;

public class LoadCasesByAccountApexUC {
    
    private final SalesforceApexCasePort salesforcePort;

    public LoadCasesByAccountApexUC(SalesforceApexCasePort salesforcePort) {
        this.salesforcePort = salesforcePort;
    }

    public Stream<CaseResponse> handle(String sfAccountId) throws JsonProcessingException {
        if(!StringUtils.hasText(sfAccountId)) {
            throw new RuntimeException("You must provide an AccountId");
        }
        SOQLQueryResponse<CaseQueryRecord> resultByAccount = salesforcePort.loadByAccount(sfAccountId);
        return resultByAccount.getRecords()
                              .stream()
                              .map(apex -> new CaseResponse(apex.getCaseId(), apex.getCaseNumber(), apex.getCaseSubject(), apex.getCaseReason(), apex.getCaseStatus(), apex.getOmniChannel(), apex.getAccountId()));
    }
}
