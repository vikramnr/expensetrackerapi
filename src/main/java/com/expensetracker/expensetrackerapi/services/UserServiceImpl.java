package com.expensetracker.expensetrackerapi.services;

import com.expensetracker.expensetrackerapi.domain.User;
import com.expensetracker.expensetrackerapi.exceptions.EtAuthException;
import com.expensetracker.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email == null)
            throw new EtAuthException("empty email id");
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User RegisterUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid Email Message");
         Integer count = userRepository.getCountByEmail(email);
         if(count>0)
             throw new EtAuthException("Email is already in use");
         Integer userId = userRepository.create(firstName, lastName, email, password);
         return userRepository.findById(userId);
    }
}
