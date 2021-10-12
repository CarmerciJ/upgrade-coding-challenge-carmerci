package com.upgrade.pojos.lead;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class LeadSecretRequest {

    private UUID loanAppUuid;
    private Boolean skipSideEffects;
}
