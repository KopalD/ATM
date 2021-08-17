package com.upstox.model;

import com.upstox.enums.CardStatus;

import java.time.LocalDate;

public class Card {
    private String userId;
    private String accountId;
    private long cardNumber;
    private String cvv;
    private String cardPin;
    private LocalDate expirationDate;
    private CardStatus status;

    public Card(){

    }

    public static Card getInstance(String userId, String accountId, long cardNumber, String cvv, String cardPin, LocalDate expirationDate, CardStatus status) {
        return new Card(userId, accountId, cardNumber, cvv, cardPin, expirationDate, status);
    }

    public Card(String userId, String accountId, long cardNumber, String cvv, String cardPin, LocalDate expirationDate, CardStatus status) {
        this.userId = userId;
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardPin = cardPin;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }
}