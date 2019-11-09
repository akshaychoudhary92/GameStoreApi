package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.SalesTaxRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesTaxRateDaoTemplateImplTest {

    @Autowired
    protected SalesTaxRateDao salesTaxRateDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getSalesTaxRate() {
        SalesTaxRate salesTaxRate = new SalesTaxRate();

        salesTaxRate = salesTaxRateDao.getSalesTaxRate("GA");

        SalesTaxRate salesTaxRate1 = salesTaxRateDao.getSalesTaxRate(salesTaxRate.getState());

        assertEquals(salesTaxRate.getRate(), salesTaxRate1.getRate());
    }
}