package com.upstox.service.impl;

import com.upstox.constants.Constants;
import com.upstox.dao.TxnDao;
import com.upstox.exception.InsufficientBalanceException;
import com.upstox.model.Account;
import com.upstox.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {



    public TransactionServiceImpl(TxnDao dao) {
        this.dao = dao;
    }

    private TxnDao dao;

    @Override
    public boolean canTransact(Double amount, Account account) {
        return account.getBalance() >= amount;
    }

    @Override
    public boolean doDebitTnx(Double amount, Account account) throws InsufficientBalanceException {
        if (canTransact(amount, account)) {
            Double currentAccountBalance = account.getBalance();
            account.setBalance(currentAccountBalance - amount);
            dao.updateAccount(account);
        } else {
            throw new InsufficientBalanceException(Constants.USER_ACCOUNT_HAS_INSUFFICIENT_BALANCE);
        }
        return true;
    }

    @Override
    public boolean doCreditTnx(Double amount, Account account) {
        Double currentAccountBalance = account.getBalance();
        account.setBalance(currentAccountBalance + amount);
        dao.updateAccount(account);
        return true;
    }
}
