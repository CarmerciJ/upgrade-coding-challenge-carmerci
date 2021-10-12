package com.upgrade.pojos.lead;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class LoanAppResumptionInfo {
    private Integer loanAppId;
    private UUID loanAppUuid;
    private String referrer;
    private String status;
    private String productType;
    private String sourceSystem;
    private BigDecimal desiredAmount;
    private BorrowerResumptionInfo borrowerResumptionInfo;
    private Object coBorrowerResumptionInfo;
    private boolean turnDown;
    private boolean hasLogin;
    private Object availableAppImprovements;
    private Object cashOutAmount;
    private boolean canAddCollateral;
    private Object rewardProgramId;
    private Object rewardProgramCode;
    private Object addon;
    private Object isMobileDiscountApplied;
    private boolean checkingDiscountAvailable;
}
