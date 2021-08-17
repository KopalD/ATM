package com.upstox.state.impl;

import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Account;
import com.upstox.model.Bill;
import com.upstox.state.ATMState;

import java.util.List;

public class CreditState extends ATMState {

    public CreditState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
    }

    @Override
    public void creditAmount(Double amount, List<Bill> bills) {
        Account account = atmServiceImpl.getSession().getAccount();
        try {
            atmServiceImpl.getBillService().validate(amount, bills);
            atmServiceImpl.getTransactionService().doCreditTnx(amount, account);
            atmServiceImpl.setState(new DisplayBalanceState(atmServiceImpl));
        } catch (Exception e) {
            atmServiceImpl.setState(new ErrorState(atmServiceImpl, e));
        }
    }
}
