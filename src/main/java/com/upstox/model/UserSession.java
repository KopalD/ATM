package com.upstox.model;

public class UserSession {
    private String sessionId;
    private Account account;

    public UserSession(String sessionId, Account account) {
        this.sessionId = sessionId;
        this.account = account;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
