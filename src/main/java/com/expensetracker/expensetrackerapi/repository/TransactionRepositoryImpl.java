package com.expensetracker.expensetrackerapi.repository;

import com.expensetracker.expensetrackerapi.domain.Transaction;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE) " +
            "VALUES(NEXTVAL('et_transaction_seq'), ?, ?, ?, ?, ?)";
    private  final static String FIND_BY_ID_SQL = "SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND " +
            "CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    private static final String FIND_ALL_SQL = "SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND " +
            "CATEGORY_ID = ?";

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return jdbcTemplate.query(FIND_ALL_SQL,new Object[]{userId, categoryId},transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL,new Object[]{userId,categoryId,transactionId}, transactionRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction Not Found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        System.out.println(amount);

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement =  con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,categoryId);
                preparedStatement.setInt(2,userId);
                preparedStatement.setDouble(3,amount);
                preparedStatement.setString(4,note);
                preparedStatement.setDouble(5,transactionDate);
                System.out.println(preparedStatement);
                return preparedStatement;
            },keyHolder);

            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }
        catch (Exception e){
            System.out.println(e);
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {

    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {

    }

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        return new Transaction(rs.getInt("TRANSACTION_ID"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE")
                );
    });
}