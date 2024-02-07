package br.com.vivo.sfclient.adapters.rest.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vivo.sfclient.application.dto.CaseResponse;
import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.application.dto.SalesforceResponse;
import br.com.vivo.sfclient.application.usecase.apex.CreateCaseApexUC;
import br.com.vivo.sfclient.application.usecase.apex.DetailCaseByIdApexUC;
import br.com.vivo.sfclient.application.usecase.apex.LoadCasesByAccountApexUC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(path = "/v1/apex", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesforceApexController {
    
    private final CreateCaseApexUC createCaseUC;
    private final DetailCaseByIdApexUC detailCaseByIdUC;
    private final LoadCasesByAccountApexUC loadCaseByAccountUC;

    public SalesforceApexController(CreateCaseApexUC createCaseUC, DetailCaseByIdApexUC detailCaseByIdUC, LoadCasesByAccountApexUC loadCaseByAccountUC) {
        this.createCaseUC = createCaseUC;
        this.detailCaseByIdUC = detailCaseByIdUC;
        this.loadCaseByAccountUC = loadCaseByAccountUC;
    }

    @GetMapping(value = "/{accountId}/cases")
    public ResponseEntity<List<CaseResponse>> loadCases(@PathVariable String accountId) {
        try {
            return new ResponseEntity<>(loadCaseByAccountUC.handle(accountId).toList(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }        
    }

    @GetMapping(value = "/cases/{caseId}")
    public ResponseEntity<CaseResponse> detailCase(@PathVariable String caseId) {
        try {
            return new ResponseEntity<>(detailCaseByIdUC.handle(caseId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }        
    }

    @PostMapping(value = "/cases")
    public ResponseEntity<SalesforceResponse> createCase(@RequestBody SalesforceRequest request) {
        try {
            return new ResponseEntity<>(createCaseUC.handle(request), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }        
    }
}
