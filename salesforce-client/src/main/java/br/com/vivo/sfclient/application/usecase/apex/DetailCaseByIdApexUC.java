package br.com.vivo.sfclient.application.usecase.apex;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vivo.sfclient.application.dto.CaseResponse;
import br.com.vivo.sfclient.domain.ports.apex.SalesforceApexCasePort;
import br.com.vivo.sfclient.domain.salesforce.apex.CaseQueryRecord;
import br.com.vivo.sfclient.model.SOQLQueryResponse;

public class DetailCaseByIdApexUC {
    
    private final SalesforceApexCasePort salesforcePort;

    public DetailCaseByIdApexUC(SalesforceApexCasePort salesforcePort) {
        this.salesforcePort = salesforcePort;
    }

    public CaseResponse handle(String sfCaseId) throws JsonProcessingException {
        if(!StringUtils.hasText(sfCaseId)) {
            throw new RuntimeException("You must provide an CaseId");
        }
        SOQLQueryResponse<CaseQueryRecord> result = salesforcePort.load(sfCaseId);
        if (result.getTotalSize() == 1) {
            CaseQueryRecord caseQueryRecord = result.getRecords().get(0);
            return new CaseResponse(caseQueryRecord.getCaseId(), caseQueryRecord.getCaseNumber(), caseQueryRecord.getCaseSubject(), caseQueryRecord.getCaseReason(), caseQueryRecord.getCaseStatus(), caseQueryRecord.getOmniChannel(), caseQueryRecord.getAccountId());
        }
        return null;
    }
}
