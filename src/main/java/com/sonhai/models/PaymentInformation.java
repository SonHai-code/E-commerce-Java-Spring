package com.sonhai.models;

import jakarta.persistence.*;

/*
    PaymentInformation - User: n - 1
* */
@Entity
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private String expirationDate;

    private String cvv;

}
