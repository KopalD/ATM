package com.upstox.dao;

import com.upstox.enums.CardStatus;
import com.upstox.model.Account;
import com.upstox.model.Card;
import com.upstox.util.HashUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class TxnDao {

    private List<Card> cards = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<String> bills = new ArrayList<>();

    public TxnDao() {
        init();
    }

    public void init() {
        cards = getCards();
        accounts = getAccounts();
        bills = getBills();
    }

    private List<String>  getBills() {
        return Arrays.asList("XXX","XXY","XZX");
    }

    public List<String> getValidBillSequences() {
        return bills;
    }

    public void updateAccount(Account acc) {
        Optional<Account> acx = accounts.stream()
                .filter(account -> account.getAccountNumber().equals(acc.getAccountNumber()))
                .findFirst();
        acx.ifPresent(account -> accounts.set(accounts.indexOf(account), acc));
    }

    public Optional<Card> getCard(long cardNumber) {
        return cards.stream()
                .filter(card -> card.getCardNumber() == cardNumber)
                .findFirst();
    }

    public Optional<Account> getAccount(String accNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accNumber))
                .findFirst();
    }

    private List<Account> getAccounts() {
        return new ArrayList<>(
                Arrays.asList(
                        Account.getInstance(

                                "1023465510292764",
                                100000
                        ),
                        Account.getInstance(

                                "1042243782084364",
                                100000
                        ),
                        Account.getInstance(

                                "4348934782397484",
                                1000
                        ),
                        Account.getInstance(

                                "4348934782397481",
                                500
                        )
                )
        );
    }

    private List<Card> getCards() {
        return new ArrayList<>(
                Arrays.asList(
                        Card.getInstance(
                                "nidhi",
                                "1023465510292764",
                                2024465520292333L,
                                "123", HashUtil.hash("1234"),
                                LocalDate.of(2021, 11, 23),
                                CardStatus.ACTIVE),
                        Card.getInstance(
                                "Niharika",
                                "1042243782084364",
                                2024465520292333L,
                                "456", HashUtil.hash("5678"),
                                LocalDate.of(2024, 02, 10),
                                CardStatus.ACTIVE),
                        Card.getInstance(
                                "Siddhi",
                                "4348934782397484",
                                4263478634786327L,
                                "789", HashUtil.hash("9012"),
                                LocalDate.of(2020, 06, 16),
                                CardStatus.BLOCKED),
                        Card.getInstance(
                                "Kopal",
                                "4348934782397481",
                                4263478634786322L,
                                "781", HashUtil.hash("9013"),
                                LocalDate.of(2021, 9, 16),
                                CardStatus.ACTIVE)
                )


        );
    }

}
