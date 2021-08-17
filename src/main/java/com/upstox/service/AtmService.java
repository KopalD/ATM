package com.upstox.service;

import com.upstox.enums.TransactionMenu;
import com.upstox.model.Bill;
import com.upstox.model.Card;
import com.upstox.state.ATMState;

import java.util.List;

public interface AtmService {
    void init();

    // For Checking & Setting Cash in M/C
    double getAvailableCash();

    void setAvailableCash(double availableCash);

    // For Checking & Setting State of M/C
    ATMState getState();

    void setState(ATMState state);

    // Action methods
    void insertCard(Card card);

    void authenticatePin(String pin);

    void selectOption(TransactionMenu menu);

    void withdraw(double amount);

    void fetchCard();

    void credit(double amount, List<Bill> billList);

    void printStatement();

    void setOutOfService();
}
