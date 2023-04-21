package com.mastercard.repository;

import com.mastercard.model.TransactionObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionObj, Integer> {

    List<TransactionObj> getAllByAccountIdOrderByTransactionDateDesc(int accountId);
}
