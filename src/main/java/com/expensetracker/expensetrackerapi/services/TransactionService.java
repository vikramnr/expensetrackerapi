package com.expensetracker.expensetrackerapi.services;

import com.expensetracker.expensetrackerapi.domain.Transaction;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface TransactionService {

    List<Transaction> fetchAllTransaction(Integer userId, Integer categoryId);

    Transaction fetchTransaction(Integer userId, Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;

    Transaction createTransaction(Integer userId, Integer categoryId,Double amount,String note,Long transactionDate) throws EtBadRequestException;

    Boolean updateTransaction(Integer userId, Integer categoryId,Integer transactionId,Transaction transaction) throws EtBadRequestException;

    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
}
