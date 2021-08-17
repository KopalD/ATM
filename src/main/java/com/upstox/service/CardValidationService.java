package com.upstox.service;

import com.upstox.model.Card;
import com.upstox.model.UserSession;

public interface CardValidationService {
    boolean validateCard(Card card);
    UserSession createUserSession(Card card, String pin) throws Exception;
}
