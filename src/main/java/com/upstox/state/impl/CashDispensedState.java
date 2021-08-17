package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class CashDispensedState extends ATMState {
    private static final Logger logger = Logger.getLogger(CashDispensedState.class.toString());

    public CashDispensedState(AtmServiceImpl atmServiceImpl) {
         super(atmServiceImpl);
    }

    @Override
    public void dispenseCash() {
        logger.info(Constants.COLLECT_YOUR_CASH);
        DisplayBalanceState displayBalanceState = new DisplayBalanceState(this.atmServiceImpl);
        this.atmServiceImpl.setState(displayBalanceState);
    }
}
