package com.upstox.service;

import com.upstox.exception.InsufficientBalanceException;
import com.upstox.model.Account;

public interface TransactionService {

    boolean canTransact(Double amount, Account account);
    boolean doDebitTnx(Double amount, Account account) throws InsufficientBalanceException;
    boolean doCreditTnx(Double amount, Account account);
}
