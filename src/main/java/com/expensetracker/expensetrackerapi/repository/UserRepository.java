package com.expensetracker.expensetrackerapi.repository;

import com.expensetracker.expensetrackerapi.domain.User;
import com.expensetracker.expensetrackerapi.exceptions.EtAuthException;

public interface UserRepository {

    Integer create(String firstName,String lastName,String email,String password) throws EtAuthException;

    User findByEmailAndPassword(String email,String password) throws EtAuthException;

    Integer getCountByEmail(String email) throws EtAuthException;

    User findById(Integer userId) throws EtAuthException    ;

}
