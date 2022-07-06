package com.expensetracker.expensetrackerapi.resources;

import com.expensetracker.expensetrackerapi.domain.Category;
import com.expensetracker.expensetrackerapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest httpServletRequest){
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        return new ResponseEntity<>(categoryService.fetchAllCategories(userId),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest httpServletRequest, @PathVariable("categoryId") Integer categoryId) {
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        Category category = categoryService.fetchCategoryById(userId,categoryId);
        return new ResponseEntity<Category>(category,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest httpServletRequest, @RequestBody Map<String,Object> categoryMap) {

        int userId  = (Integer) httpServletRequest.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category newCategory  = categoryService.addCategory(userId,title,description);
        System.out.println(newCategory.getCategoryId());
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest httpServletRequest,@PathVariable("categoryId") Integer categoryId,@RequestBody Category updateCategory) {
        int userId = (Integer) httpServletRequest.getAttribute("userId");
        categoryService.updateCategory(userId,categoryId,updateCategory);
        Map<String,Boolean> map = new HashMap<>();
        map.put("Success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
