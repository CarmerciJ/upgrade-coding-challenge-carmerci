package com.upgrade.pojos;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borrower {

    @Setter @Getter String firstName;
    @Setter @Getter String lastName;
    @Setter @Getter String dob;
    @Setter @Getter String city;
    @Setter @Getter String state;
    @Setter @Getter String zipCode;
    @Setter @Getter String street;
    @Setter @Getter String email;
    @Setter @Getter String password;
    @Setter @Getter BigDecimal desiredLoanAmount;
    @Setter @Getter String loanPurpose;
    @Setter @Getter BigDecimal yearlyIncome;
    @Setter @Getter BigDecimal additionalIncome;

}

