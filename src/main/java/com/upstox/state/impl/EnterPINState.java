package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Card;
import com.upstox.model.UserSession;
import com.upstox.service.CardValidationService;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class EnterPINState extends ATMState {
    private static final Logger logger = Logger.getLogger(EnterPINState.class.toString());
    private Card card;
    public EnterPINState(AtmServiceImpl atmServiceImpl, Card card) {
        super(atmServiceImpl);
        this.card = card;
    }

    @Override
    public boolean authenticatePin(String pin) {
        logger.info(Constants.AUTHENTICATING_PIN);
        try {
            CardValidationService validationService = this.atmServiceImpl.getValidationService();
            UserSession session = validationService.createUserSession(this.card, pin);
            this.atmServiceImpl.setSession(session);
            this.atmServiceImpl.setState(new TransactionMenuState(this.atmServiceImpl));
            return true;
        } catch (Exception e) {
            this.atmServiceImpl.setState(new ErrorState(this.atmServiceImpl, new Exception(e)));
            return false;
        }
    }

    @Override
    public void exit() {
        this.atmServiceImpl.setState(new ExitState(this.atmServiceImpl));
    }

}
