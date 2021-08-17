package com.upstox.state.impl;

import com.upstox.constants.Constants;
import com.upstox.exception.InvalidCardException;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Card;
import com.upstox.service.CardValidationService;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class ReadyState extends ATMState {
    private static final Logger logger = Logger.getLogger(ReadyState.class.toString());
    public ReadyState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
    }

    @Override
    public void readCard(Card card) {
        logger.info(Constants.READING_CARD);
        CardValidationService validationService = this.atmServiceImpl.getValidationService();
        boolean isCardValid = validationService.validateCard(card);
        if (isCardValid) {
            this.atmServiceImpl.setState(new EnterPINState(this.atmServiceImpl, card));
        } else {
            this.atmServiceImpl.setState(new ErrorState(this.atmServiceImpl, new InvalidCardException(Constants.INVALID_CARD_INSERTED)));
        }
    }
}
