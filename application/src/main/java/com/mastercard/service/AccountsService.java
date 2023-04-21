package com.mastercard.service;

import com.mastercard.generated.model.Accounts;
import com.mastercard.generated.model.Transaction;

import java.util.List;

public interface AccountsService {

    public Accounts getAccountBalance(int accountId);

    public List<Transaction> getMiniTransactions(String accountId);
}
