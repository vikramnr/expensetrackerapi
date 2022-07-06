package com.expensetracker.expensetrackerapi.repository;
import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.expensetracker.expensetrackerapi.domain.Category;
import com.expensetracker.expensetrackerapi.exceptions.EtBadRequestException;
import com.expensetracker.expensetrackerapi.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements  CategoryRepository{

    private static final String SQL_CREATE = "INSERT INTO ET_CATEGORIES(CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) " +
            "VALUES (NEXTVAL('ET_CATEGORIES_SEQ'), ? , ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID,C.USER_ID,C.TITLE,C.DESCRIPTION," +
            "COALESCE(SUM(T.AMOUNT),0) TOTAL_EXPENSE "+
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_FIND_ALL =  "SELECT C.CATEGORY_ID,C.USER_ID,C.TITLE,C.DESCRIPTION," +
            "COALESCE(SUM(T.AMOUNT),0) TOTAL_EXPENSE "+
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_UPDATE = "UPDATE ET_CATEGORIES SET TITLE=?, DESCRIPTION=? " +
            "WHERE USER_ID=? AND CATEGORY_ID=?"
            ;
//"SELECT C.CATEGORY_ID,C.USER_ID,C.TITLE,C.DESCRIPTION,0 TOTAL_EXPENSE FROM ET_CATEGORIES C LIMIT 1";


    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL,new Object[]{userId},categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId},categoryRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Category Not Found");
        }
    }

    @Override
    public Integer Create(Integer userId, String title, String description) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,userId);
                ps.setString(2,title);
                ps.setString(3,description);
                return ps;
            },keyHolder);
            System.out.println(keyHolder.getKeys().get("CATEGORY_ID")+"CATEGORY ID FROM CREATE");
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }

    }

    @Override
    public void Update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,new Object[]{category.getTitle(),category.getDescription(),userId,categoryId});
        }
        catch (Exception e) {
            throw new EtBadRequestException("Invalid Update parameters");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private RowMapper<Category> categoryRowMapper = ((rs,rowNum) -> {
        return new Category(rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("TOTAL_EXPENSE") );
    });
}
