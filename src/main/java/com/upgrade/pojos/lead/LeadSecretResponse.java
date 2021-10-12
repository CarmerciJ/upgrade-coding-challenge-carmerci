package com.upgrade.pojos.lead;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LeadSecretResponse {
    private LoanAppResumptionInfo loanAppResumptionInfo;
    private List<Object> offers;
    private Object selectedOffer;
    private List<Object> requiredAgreements;
    private List<String> resetOptions;
    private Object originalLoanApp;
}
