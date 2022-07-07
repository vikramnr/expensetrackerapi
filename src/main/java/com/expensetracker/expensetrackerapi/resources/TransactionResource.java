package com.expensetracker.expensetrackerapi.resources;

import com.expensetracker.expensetrackerapi.domain.Transaction;
import com.expensetracker.expensetrackerapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {
    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest httpServletRequest,
                                                      @PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody Map<String,Object> transactionMap) {
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        Double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = transactionMap.get("note").toString();
        Long transactionDate = Long.valueOf(transactionMap.get("transactionDate").toString());

        Transaction transaction = transactionService.createTransaction(userId,categoryId,amount,note,transactionDate);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> findAllTransaction(HttpServletRequest httpServletRequest, @PathVariable("categoryId") Integer categoryId) {
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransaction(userId,categoryId);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<Transaction> findTransactionById(HttpServletRequest httpServletRequest,@PathVariable("categoryId") Integer categoryId,@PathVariable("transactionId") Integer transactionId) {
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransaction(userId,categoryId,transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }
}
