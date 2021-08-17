package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.enums.TransactionMenu;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class ErrorState extends ATMState {
    private static final Logger logger = Logger.getLogger(ErrorState.class.toString());
    private Exception e;

    public ErrorState(AtmServiceImpl atmServiceImpl, Exception e){
        super(atmServiceImpl);
        this.e = e;
        logger.severe(Constants.ERROR_ENCOUNTERED + e.getMessage());
    }

    @Override
    public void selectTransactionOption(TransactionMenu selection) {
         this.atmServiceImpl.setState(new TransactionMenuState(this.atmServiceImpl));
    }

    @Override
    public void exit() {
       this.atmServiceImpl.setState(new ExitState(this.atmServiceImpl));
    }


}
