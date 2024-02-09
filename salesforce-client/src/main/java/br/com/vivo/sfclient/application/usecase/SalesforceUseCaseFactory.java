package br.com.vivo.sfclient.application.usecase;

import java.util.Map;
import java.util.Optional;

import br.com.vivo.sfclient.domain.myrequest.BaseCase.SalesforceReason;

public class SalesforceUseCaseFactory {
    
    @SuppressWarnings("rawtypes")
    private final Map<SalesforceReason, BaseUseCase> useCases;

    public SalesforceUseCaseFactory(CreateOrderTrackingCaseUC createOrderTrackingCaseUC,
                                    CreateChangeAddressCaseUC createChangeAddressCaseUC) {
        this.useCases = Map.of(
            SalesforceReason.CHANGE_ADDRESS, createChangeAddressCaseUC,
            SalesforceReason.TRACKING_PROBLEM, createOrderTrackingCaseUC
        );
    }

    @SuppressWarnings("rawtypes")
    public Optional<BaseUseCase> getUseCase(SalesforceReason reason) {
        return Optional.ofNullable(useCases.get(reason));
    }
}
