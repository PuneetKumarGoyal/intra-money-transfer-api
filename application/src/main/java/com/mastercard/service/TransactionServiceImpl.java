package com.mastercard.service;

import com.mastercard.generated.model.Transfer;
import com.mastercard.model.AccountObj;
import com.mastercard.model.TransactionObj;
import com.mastercard.repository.AccountsRepository;
import com.mastercard.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private AccountsRepository accountsRepository;
    private TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionServiceImpl(AccountsRepository accountsRepository,
            TransactionsRepository transactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override

    public String transfer(Transfer transfer) {

        //first check whether account exists
        var senderAccountOptional = accountsRepository.findById(
                Integer.valueOf(transfer.getFromAccount()));
        var receiverAccountOptional = accountsRepository.findById(
                Integer.valueOf(transfer.getToAccount()));

        if(senderAccountOptional.isPresent() && receiverAccountOptional.isPresent()) {
            var senderAccount = senderAccountOptional.get();
            var receiverAccount = receiverAccountOptional.get();
            //check whether account has balance to be sent
            var senderAccountBalance = senderAccount.getBalance();
            if(senderAccountBalance >= transfer.getAmount()) {
                return debitCreditAccountsAndCreateTransactions(transfer, senderAccount, receiverAccount);
            } else {
                throw new IllegalArgumentException("Insufficient funds available");
            }
        } else {
            throw new IllegalArgumentException("Accounts not exist");
        }
    }

    @Transactional
    private String debitCreditAccountsAndCreateTransactions(Transfer transfer, AccountObj senderAccount,
                                                            AccountObj receiverAccount) {

        //Debit the sender account
        senderAccount.setBalance(senderAccount.getBalance() - transfer.getAmount());
        accountsRepository.save(senderAccount);

        //Credit the receiver account
        receiverAccount.setBalance(receiverAccount.getBalance() + transfer.getAmount());
        accountsRepository.save(receiverAccount);

        //Create transaction for sender account
        transactionsRepository.save(TransactionObj.builder()
                .accountId(Integer.valueOf(transfer.getFromAccount()))
                .otherAccountId(Integer.valueOf(transfer.getToAccount()))
                .amount(transfer.getAmount())
                .type("Debit")
                .transactionDate(LocalDateTime.now())
                .currency("EUR").build());

        //Create transaction for receiver account
        transactionsRepository.save(TransactionObj.builder()
                .accountId(Integer.valueOf(transfer.getToAccount()))
                .otherAccountId(Integer.valueOf(transfer.getFromAccount()))
                .amount(transfer.getAmount())
                .type("Credit")
                .transactionDate(LocalDateTime.now())
                .currency("EUR").build());

        return "Transaction successfully executed";
    }
}
