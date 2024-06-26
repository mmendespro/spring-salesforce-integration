package br.com.demo.sfclient.adapters.rest.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.sfclient.application.dto.SalesforceRequest;
import br.com.demo.sfclient.application.usecase.SalesforceUseCaseFactory;
import br.com.demo.sfclient.domain.myrequest.BaseCase.SalesforceReason;
import br.com.demo.sfclient.model.composite.CompositeEntityRecordResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping(path = "/v1/composite", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesforceCompositeController {
   
    private final SalesforceUseCaseFactory caseFactory;

    public SalesforceCompositeController(SalesforceUseCaseFactory caseFactory) {
        this.caseFactory = caseFactory;
    }

    @PostMapping
    public ResponseEntity<CompositeEntityRecordResponse.Result> createSalesforceCase(@RequestBody SalesforceRequest request) {
         try {
            var reason = SalesforceReason.valueOf(request.get("salesforceReason").toString());
            var usecase = caseFactory.getUseCase(reason).orElseThrow(() -> new RuntimeException("Invalid Salesforce Reason"));
            Optional<CompositeEntityRecordResponse.Result> newCaseOpt = usecase.handle(request)
                                                                               .getCompositeResponse()
                                                                               .stream()
                                                                               .filter(resp -> resp.getReferenceId().equals("NewCase"))
                                                                               .findFirst();
            return new ResponseEntity<>(newCaseOpt.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Some error has occurred {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }    
    }
}
