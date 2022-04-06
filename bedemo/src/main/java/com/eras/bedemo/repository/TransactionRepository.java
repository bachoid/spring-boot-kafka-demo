package com.eras.bedemo.repository;

import org.springframework.data.repository.CrudRepository;

import com.eras.bedemo.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
