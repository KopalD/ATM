package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class DisplayBalanceState extends ATMState {
    private static final Logger logger = Logger.getLogger(DisplayBalanceState.class.toString());

    public DisplayBalanceState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
        logger.info(Constants.YOUR_CURRENT_BALANCE + atmServiceImpl.getSession().getAccount().getBalance());
    }

    @Override
    public void printStatement() {
        logger.info(Constants.STATEMENT_START);
        logger.info(Constants.TIMESTAMP + LocalDateTime.now());
        logger.info(Constants.ACCOUNT_NO + atmServiceImpl.getSession().getAccount().getAccountNumber());
        logger.info(Constants.YOUR_CURRENT_BALANCE + atmServiceImpl.getSession().getAccount().getBalance());
        logger.info(Constants.STATEMENT_END);
    }

    @Override
    public void exit() {
        this.atmServiceImpl.setState(new ExitState(this.atmServiceImpl));
    }


}
