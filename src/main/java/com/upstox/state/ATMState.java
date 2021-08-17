package com.upstox.state;

import com.upstox.enums.TransactionMenu;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Bill;
import com.upstox.model.Card;
import com.upstox.state.impl.OutOfServiceState;

import java.util.List;

public abstract class ATMState {

    public AtmServiceImpl atmServiceImpl;

    public ATMState(AtmServiceImpl atmServiceImpl) {
        this.atmServiceImpl = atmServiceImpl;
    }

    public void readCard(Card card) {
        throw new IllegalStateException();
    }

    public boolean authenticatePin(String pin) {
        throw new IllegalStateException();
    }

    public void selectTransactionOption(TransactionMenu selection) {
        throw new IllegalStateException();
    }

    public void creditAmount(Double amount, List<Bill> bills) {
        throw new IllegalStateException();
    }

    public void debitAmount(Double amount) {
        throw new IllegalStateException();
    }

    public void dispenseCash() {
        throw new IllegalStateException();
    }

    public void exit() {
        throw new IllegalStateException();
    }

    public void returnCard() {
        throw new IllegalStateException();
    }

    public void printStatement() {
        throw new IllegalStateException();
    }

    public void outOfService() {
        this.atmServiceImpl.setState(new OutOfServiceState(this.atmServiceImpl));
    }
}
