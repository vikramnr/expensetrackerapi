package com.expensetracker.expensetrackerapi.repository;
import java.util.List;
import com.expensetracker.expensetrackerapi.domain.Category;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws EtResourceNotFoundException;

    Category findById(Integer userId,Integer categoryId) throws EtResourceNotFoundException;

    Integer Create(Integer userId,String title,String description) throws EtBadRequestException;

    void Update(Integer userId,Integer categoryId,Category category) throws EtBadRequestException;

    void removeById(Integer userId, Integer categoryId);



}
