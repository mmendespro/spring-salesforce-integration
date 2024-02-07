package br.com.vivo.sfclient.adapters.rest.api;

import java.util.Map;

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

import br.com.vivo.sfclient.application.dto.SalesforceRequest;
import br.com.vivo.sfclient.application.usecase.composite.CreateCaseCompositeUC;
import br.com.vivo.sfclient.application.usecase.composite.ListCaseCompositeUC;
import br.com.vivo.sfclient.model.composite.CompositeEntityRecordResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(path = "/v1/composite", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesforceCompositeController {
    
    private final ListCaseCompositeUC listCaseCompositeUC;
    private final CreateCaseCompositeUC createCaseCompositeUC;

    public SalesforceCompositeController(ListCaseCompositeUC listCaseCompositeUC,
            CreateCaseCompositeUC createCaseCompositeUC) {
        this.listCaseCompositeUC = listCaseCompositeUC;
        this.createCaseCompositeUC = createCaseCompositeUC;
    }

    @GetMapping(value = "/{accountId}/cases")
    public ResponseEntity<Map<String, Object>> loadCases(@PathVariable String accountId) {
        try {
            return new ResponseEntity<>(listCaseCompositeUC.handle(accountId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }        
    }

    @PostMapping(value = "/cases")
    public ResponseEntity<CompositeEntityRecordResponse> createCase(@RequestBody SalesforceRequest request) {
        try {
            return new ResponseEntity<>(createCaseCompositeUC.handle(request), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }        
    }    
}
