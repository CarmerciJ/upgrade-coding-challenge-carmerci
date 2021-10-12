package com.upgrade.pojos.lead;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerResumptionInfo {
    private String firstName;
    private String maskedEmail;
    private boolean ssnRequired;
    private String state;
    private String email;
}
