package com.upstox.service.impl;

import com.upstox.constants.Constants;
import com.upstox.dao.TxnDao;
import com.upstox.enums.CardStatus;
import com.upstox.exception.InvalidCardException;
import com.upstox.exception.InvalidCardPinException;
import com.upstox.model.Account;
import com.upstox.model.Card;
import com.upstox.model.UserSession;
import com.upstox.service.CardValidationService;
import com.upstox.util.HashUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class CardServiceImpl implements CardValidationService {

    public CardServiceImpl(TxnDao dao) {
        this.dao = dao;
    }

    private TxnDao dao;

    @Override
    public boolean validateCard(Card card) {
        Optional<Card> cardEntity = dao.getCard(card.getCardNumber());
        return cardEntity.map(c -> doBasicVerification(c)).orElse(false);
    }

    @Override
    public UserSession createUserSession(Card card, String pin) throws InvalidCardException, InvalidCardPinException {
        Optional<Card> cardEntity = dao.getCard(card.getCardNumber());
        String hashedPin = HashUtil.hash(pin);
        if (!cardEntity.isPresent()) {
            throw new InvalidCardException(Constants.INVALID_CARD_INSERTED);
        } else {
            if (cardEntity.get().getCardPin().equals(hashedPin)) {
                Optional<Account> account = dao.getAccount(cardEntity.get().getAccountId());
                UserSession session = new UserSession(UUID.randomUUID().toString(), account.get());
                return session;
            } else {
                throw new InvalidCardPinException(Constants.INVALID_PIN_ENTERED);
            }
        }
    }

    private Boolean doBasicVerification(Card c) {
        if (!c.getStatus().equals(CardStatus.ACTIVE)) {
            return false;
        }
        if (c.getExpirationDate().isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
