package com.mastercard.controller;

import com.mastercard.generated.controller.TransferApi;
import com.mastercard.generated.model.Transfer;
import com.mastercard.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class TransactionsController implements TransferApi {

    private TransactionService transactionService;

    @Autowired
    void TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<String> transfer(@Valid @RequestBody Transfer transfer) {
        return ResponseEntity.ok(transactionService.transfer(transfer));
    }
}
