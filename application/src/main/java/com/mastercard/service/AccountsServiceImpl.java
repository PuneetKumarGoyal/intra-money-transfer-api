package com.mastercard.service;

import com.mastercard.generated.model.Accounts;
import com.mastercard.generated.model.Transaction;
import com.mastercard.model.AccountObj;
import com.mastercard.model.TransactionObj;
import com.mastercard.repository.AccountsRepository;
import com.mastercard.repository.TransactionsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountsServiceImpl implements AccountsService {

    private AccountsRepository accountsRepository;
    private TransactionsRepository transactionsRepository;

    @Autowired
    public AccountsServiceImpl(AccountsRepository accountsRepository,
                               TransactionsRepository transactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Accounts getAccountBalance(int accountId) {
        AccountObj account = accountsRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Invalid account details"));
        return accountMapper(account);
    }

    @Override
    public List<Transaction> getMiniTransactions(String accountId) {
        var accountOptional = accountsRepository.findById(Integer.valueOf(accountId));
        if(accountOptional.isEmpty()) {
            throw new EntityNotFoundException("Invalid account details");
        }
        var transactions = transactionsRepository.getAllByAccountIdOrderByTransactionDateDesc(Integer.valueOf(accountId));
        var transactionList = transactionMapper(transactions);
        if (null == transactionList || transactionList.isEmpty()) {
            throw new EntityNotFoundException("No transactions found");
        }
        return transactionList.stream().limit(20).collect(Collectors.toList());
    }

    private Accounts accountMapper(AccountObj account) {
        var accounts = new Accounts();
        accounts.setAccountId(account.getAccountId());
        accounts.setBalance(account.getBalance());
        accounts.setCurrency(account.getCurrency());
        return accounts;
    }
    private List<Transaction> transactionMapper(List<TransactionObj> transactionObjs) {
        return Optional.ofNullable(transactionObjs)
                .stream()
                .flatMap(List::stream)
                .map(transactionObj -> {
                    var t = new Transaction();
                    t.setAccountId(transactionObj.getOtherAccountId());
                    t.setCurrency(transactionObj.getCurrency());
                    t.setAmount(transactionObj.getAmount());
                    t.setType(transactionObj.getType());
                    t.setTransactionDate(transactionObj.getTransactionDate().toLocalDate());
                    return t;
                }).collect(Collectors.toList());
    }

}
