package com.postnord.ndm.credit.repository.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;


@Entity
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class CreditCardEntity extends PanacheEntity {

    public String ownerName;
    public String cardNumber;
    public int expirationMonth;
    public int expirationYear;
    public int securityCode;
    public float availableCredit;

}