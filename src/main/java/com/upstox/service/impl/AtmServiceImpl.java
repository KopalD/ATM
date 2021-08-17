package com.upstox.service.impl;

import com.upstox.dao.TxnDao;
import com.upstox.enums.TransactionMenu;
import com.upstox.model.Bill;
import com.upstox.model.Card;
import com.upstox.model.UserSession;
import com.upstox.service.AtmService;
import com.upstox.service.BillService;
import com.upstox.service.CardValidationService;
import com.upstox.service.TransactionService;
import com.upstox.state.ATMState;
import com.upstox.state.impl.ReadyState;

import java.util.List;
import java.util.logging.Logger;

public class AtmServiceImpl implements AtmService {

    private static final Logger logger = Logger.getLogger("ATM.class");

    // State & Variables
    private ATMState state;
    private UserSession session;
    private double availableCash;

    // Services
    private CardValidationService validationService;
    private TransactionService transactionService;
    private BillService billService;

    // DAO
    private TxnDao txnDao;

    @Override
    public void init() {
        this.availableCash = 100000000.00;
        this.state = new ReadyState(this);
        this.txnDao = new TxnDao();
        this.validationService = new CardServiceImpl(txnDao);
        this.transactionService = new TransactionServiceImpl(txnDao);
        this.billService = new BillServiceImpl(txnDao);
    }

    // For Checking & Setting Current User's Session
    public UserSession getSession() {
        return session;
    }

    public void setSession(UserSession session) {
        this.session = session;
    }

    public void closeSession() {
        this.session = null;
    }

    // For Checking & Setting Cash in M/C
    @Override
    public double getAvailableCash() {
        return availableCash;
    }
    @Override
    public void setAvailableCash(double availableCash) {
        this.availableCash = availableCash;
    }


    // For Checking & Setting State of M/C
    @Override
    public ATMState getState() {
        return state;
    }
    @Override
    public void setState(ATMState state) {
        this.state = state;
    }


    // Getters and Setters for services
    // Card Validation Service
    public CardValidationService getValidationService() {
        return validationService;
    }

    public void setValidationService(CardValidationService validationService) {
        this.validationService = validationService;
    }

    // Transaction Service
    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Bill & Currency Checking Service
    public BillService getBillService() {
        return billService;
    }

    public void setBillService(BillService billService) {
        this.billService = billService;
    }

    // Action methods
    @Override
    public void insertCard(Card card) {
        this.state.readCard(card);
    }
    @Override
    public void authenticatePin(String pin) {
        if(this.state.authenticatePin(pin)){
            printUserMessage();
        }
    }
    @Override
    public void selectOption(TransactionMenu menu) {
        this.state.selectTransactionOption(menu);
    }

    @Override
    public void withdraw(double amount) {
        this.state.debitAmount(amount);
    }

    @Override
    public void fetchCard() {
        this.state.exit();
        this.state.returnCard();
    }
    @Override
    public void credit(double amount, List<Bill> billList){
        this.state.creditAmount(amount, billList);
    }

    @Override
    public void printStatement() {
        this.state.printStatement();
    }

    @Override
    public void setOutOfService() {
        this.state.outOfService();
    }

    public void printUserMessage(){
        logger.info(" Please select an option to continue.");
        logger.info(" DEBIT");
        logger.info(" CREDIT");
        logger.info(" DISPLAY BALANCE");
    }

}
