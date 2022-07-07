package com.expensetracker.expensetrackerapi.repository;

import com.expensetracker.expensetrackerapi.domain.Transaction;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;
import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll(Integer userId, Integer categoryId);
    Transaction findById(Integer userId, Integer categoryId, Integer transactionId);
    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;
    void removeById(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;
}
