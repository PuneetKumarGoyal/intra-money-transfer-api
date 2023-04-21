package com.mastercard.repository;

import com.mastercard.model.AccountObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<AccountObj, Integer> {
}
