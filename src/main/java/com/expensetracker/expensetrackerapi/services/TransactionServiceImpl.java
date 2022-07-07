package com.expensetracker.expensetrackerapi.services;

import com.expensetracker.expensetrackerapi.domain.Transaction;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.expensetracker.expensetrackerapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> fetchAllTransaction(Integer userId, Integer categoryId) {
        return transactionRepository.findAll(userId,categoryId);
    }

    @Override
    public Transaction fetchTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        try{
            return transactionRepository.findById(userId, categoryId, transactionId);
        }
        catch (Exception e){
            throw new EtResourceNotFoundException("Invalid parameters");
        }
    }

    @Override
    public Transaction createTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int transactionId = transactionRepository.create(userId,categoryId,amount,note,transactionDate);
        Transaction transaction = transactionRepository.findById(userId,categoryId,transactionId);
        return transaction;
    }

    @Override
    public Boolean updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        try {
            transactionRepository.update(userId, categoryId, transactionId, transaction);
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            throw new EtBadRequestException("Unable to update transaction");
        }
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        transactionRepository.removeById(userId,categoryId,transactionId);
    }
}
