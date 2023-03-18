package com.postnord.model.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class CreditCardEntity extends PanacheEntity {
    public String ownerName;
    public String cardNumber;
    public int expirationMonth;
    public int expirationYear;
    public int securityCode;
    public float availableCredit;
}
