package com.upstox.model;

public class Bill {
    private double denomination;
    private String billNumber;

    public Bill() {
    }

    public Bill(double denomination, String billNumber) {
        this.denomination = denomination;
        this.billNumber = billNumber;
    }

    public double getDenomination() {
        return denomination;
    }

    public void setDenomination(double denomination) {
        this.denomination = denomination;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
}

