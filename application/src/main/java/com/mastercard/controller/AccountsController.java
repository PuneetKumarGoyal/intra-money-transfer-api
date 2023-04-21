package com.mastercard.controller;

import com.mastercard.generated.controller.AccountIdApi;
import com.mastercard.generated.model.Accounts;
import com.mastercard.generated.model.Transaction;
import com.mastercard.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AccountsController implements AccountIdApi {

    private AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    public ResponseEntity<Accounts> getAccountBalance(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(accountsService.getAccountBalance(Integer.valueOf(accountId)));
    }

    public ResponseEntity<List<Transaction>> getMiniTransactions(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(accountsService.getMiniTransactions(accountId));
    }
}