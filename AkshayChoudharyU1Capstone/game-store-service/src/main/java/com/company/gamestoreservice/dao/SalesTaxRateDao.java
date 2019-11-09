package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.SalesTaxRate;

public interface SalesTaxRateDao {
    SalesTaxRate getSalesTaxRate(String state);
}
