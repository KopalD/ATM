package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class ExitState extends ATMState {
    private static final Logger logger = Logger.getLogger(ExitState.class.toString());

    public ExitState(AtmServiceImpl atmServiceImpl){
        super(atmServiceImpl);
    }

    @Override
    public void returnCard() {
        logger.info(Constants.TRANSACTION_COMPLETED_MESSAGE);
        this.atmServiceImpl.closeSession();

    }

    @Override
    public void exit() {
        this.atmServiceImpl.setState(new ReadyState(this.atmServiceImpl));
    }
}
