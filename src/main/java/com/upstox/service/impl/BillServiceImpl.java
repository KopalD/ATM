package com.upstox.service.impl;

import com.upstox.constants.Constants;
import com.upstox.dao.TxnDao;
import com.upstox.exception.InvalidAmountException;
import com.upstox.exception.InvalidBillException;
import com.upstox.model.Bill;
import com.upstox.service.BillService;

import java.util.List;

public class BillServiceImpl implements BillService {



    public BillServiceImpl(TxnDao dao) {
        this.dao = dao;
    }

    private TxnDao dao;

    @Override
    public boolean validate(double amount, List<Bill> bills) throws InvalidBillException, InvalidAmountException {
        double enteredAmount = 0.0;
        if (amount == 0 && amount % 100 != 0) {
            throw new InvalidAmountException(Constants.INVALID_BILLS_PROVIDED);
        }

        if (!verifyBills(bills)) {
            throw new InvalidBillException("Invalid/Fake Bills provided.");
        }

        enteredAmount = bills.stream().mapToDouble(Bill::getDenomination).sum();

        if (enteredAmount != amount) {
            throw new InvalidAmountException(Constants.MISMATCHED_AMOUNT_WITH_BILLS);
        }

        return true;
    }

    public boolean verifyBills(List<Bill> bills) {
        List<String> sequences = dao.getValidBillSequences();
        return bills.stream().allMatch(b -> checkSeq(b, sequences));
    }

    private boolean checkSeq(Bill b, List<String> sequences) {
        return sequences.stream()
                .anyMatch(seq -> b.getBillNumber().startsWith(seq));
    }
}
