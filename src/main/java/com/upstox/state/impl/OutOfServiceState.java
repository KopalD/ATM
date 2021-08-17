package com.upstox.state.impl;

import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;

import java.util.logging.Logger;

public class OutOfServiceState extends ATMState {
    private static final Logger logger = Logger.getLogger(OutOfServiceState.class.toString());

    public OutOfServiceState(AtmServiceImpl atmServiceImpl) {
        super(atmServiceImpl);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OutOfServiceState;
    }
}
