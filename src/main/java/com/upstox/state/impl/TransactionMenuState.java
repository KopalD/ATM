package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.enums.TransactionMenu;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class TransactionMenuState extends ATMState {
    private static final Logger logger = Logger.getLogger(TransactionMenuState.class.toString());


    public TransactionMenuState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
    }

    @Override
    public void selectTransactionOption(TransactionMenu selection) {
        switch (selection) {
            case DEBIT: {
                this.atmServiceImpl.setState(new DebitState(this.atmServiceImpl));
                logger.info(Constants.ENTER_DEBIT_AMOUNT);
                break;
            }
            case CREDIT: {
                this.atmServiceImpl.setState(new CreditState(this.atmServiceImpl));
                logger.info(Constants.AMOUNT_TO_BE_DEPOSITED);
                break;
            }
            case DISPLAY_BALANCE: {
                logger.info(Constants.DISPLAY_YOUR_CURRENT_BALANCE);
                this.atmServiceImpl.setState(new DisplayBalanceState(this.atmServiceImpl));
                break;
            }
            default: {
                System.out.println(Constants.INVALID_CHOICE_PLEASE_TRY_AGAIN);
                exit();
            }
        }
    }

    @Override
    public void exit() {
        logger.info("Exiting.");
        this.atmServiceImpl.setState(new ExitState(this.atmServiceImpl));
    }


}
