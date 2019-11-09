package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProcessingFeeDaoJdbcTemplateImpl implements ProcessingFeeDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_PROCESSING_FEE =
            "select * from processing_fee where product_type = ?";

    @Autowired
    public ProcessingFeeDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProcessingFee getProcessingFee(String productType) {
        try{
            ProcessingFee processingFee = jdbcTemplate.queryForObject(SELECT_PROCESSING_FEE, this::mapToRowProcessingFee, productType);
            return processingFee;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private ProcessingFee mapToRowProcessingFee(ResultSet rs, int rowNum) throws SQLException {

        ProcessingFee pf = new ProcessingFee();
        pf.setProductType(rs.getString("product_type"));
        pf.setFee(rs.getBigDecimal("fee"));
        return pf;
    }
}
