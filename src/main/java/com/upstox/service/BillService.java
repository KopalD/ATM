package com.upstox.service;

import com.upstox.exception.InvalidAmountException;
import com.upstox.exception.InvalidBillException;
import com.upstox.model.Bill;

import java.util.List;

public interface BillService {

    boolean validate(double amount, List<Bill> bills) throws InvalidBillException, InvalidAmountException;

}
