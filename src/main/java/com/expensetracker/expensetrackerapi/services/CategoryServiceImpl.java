package com.expensetracker.expensetrackerapi.services;

import com.expensetracker.expensetrackerapi.domain.Category;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.expensetracker.expensetrackerapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws EtBadRequestException {
        int categoryId = categoryRepository.Create(userId,title,description);
        return fetchCategoryById(userId,categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        categoryRepository.Update(userId,categoryId,category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException {

    }
}