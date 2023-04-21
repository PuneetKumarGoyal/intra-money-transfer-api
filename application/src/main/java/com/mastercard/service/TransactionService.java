package com.mastercard.service;

import com.mastercard.generated.model.Transfer;

public interface TransactionService {
    String transfer(Transfer transfer);
}
