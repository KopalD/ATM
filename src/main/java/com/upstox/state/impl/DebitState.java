package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.exception.InsufficientBalanceException;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.service.TransactionService;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class DebitState extends ATMState {
    private static final Logger logger = Logger.getLogger(DebitState.class.toString());
    public DebitState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
    }

    @Override
    public void debitAmount(Double amount) {
        logger.info(Constants.DEDUCTING_AMOUNT);
        TransactionService transactionService = this.atmServiceImpl.getTransactionService();
        try {
            if (!isCashAvailable(amount)) {
                throw new InsufficientBalanceException(Constants.NOT_ENOUGH_CASH);
            }
            transactionService.doDebitTnx(amount, atmServiceImpl.getSession().getAccount());
            atmServiceImpl.setAvailableCash(atmServiceImpl.getAvailableCash() - amount);
            CashDispensedState cashDispensedState = new CashDispensedState(atmServiceImpl);
            atmServiceImpl.setState(cashDispensedState);
            cashDispensedState.dispenseCash();
        } catch (Exception e) {
            atmServiceImpl.setState(new ErrorState(atmServiceImpl, e));
        }
    }

    private boolean isCashAvailable(double amount) {
        if (atmServiceImpl.getAvailableCash() >= amount) {
            return true;
        }
        return false;
    }
}
