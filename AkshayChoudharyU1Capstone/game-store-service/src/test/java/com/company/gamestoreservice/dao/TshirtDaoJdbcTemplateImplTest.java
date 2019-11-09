package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Tshirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TshirtDaoJdbcTemplateImplTest {


    @Autowired
    private TshirtDao tShirtDao;

    @Before
    public void setUp() throws Exception {
        List<Tshirt> tShirtList = tShirtDao.getAllTshirts();

        tShirtList.stream()
                .forEach(tShirt -> tShirtDao.deleteTshirt(tShirt.gettShirtId()));
    }

    @Test
    public void addGetDeleteTshirt() {
        Tshirt tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("Orange");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        Tshirt tShirt1 = tShirtDao.getTshirt(tShirt.gettShirtId());
        assertEquals(tShirt1, tShirt);


        tShirtDao.deleteTshirt(tShirt.gettShirtId());
        tShirt1 = tShirtDao.getTshirt(tShirt.gettShirtId());
        assertNull(tShirt1);
    }



    @Test
    public void getAllTshirts() {
        Tshirt tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("Orange");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("LArge");
        tShirt.setColor("cyan");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("34.99"));
        tShirt.setQuantity(8);

        tShirt = tShirtDao.addTshirt(tShirt);

        List<Tshirt> tShirtList = tShirtDao.getAllTshirts();
        assertEquals(2, tShirtList.size());
    }

    @Test
    public void updateTshirt() {
        Tshirt tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("Orange");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt.setSize("Medium");
        tShirt.setColor("Blue");
        tShirt.setDescription("cool shirt bro");
        tShirt.setPrice(new BigDecimal("88.99"));
        tShirt.setQuantity(1);

        tShirtDao.updateTshirt(tShirt);

        Tshirt tShirt1 = tShirtDao.getTshirt(tShirt.gettShirtId());
        assertEquals(tShirt1, tShirt);
    }



    @Test
    public void getTshirtByColor() {
        Tshirt tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("Orange");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("L");
        tShirt.setColor("Yellow");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("17.99"));
        tShirt.setQuantity(3);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("white");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("XL");
        tShirt.setColor("Yellow");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("17.99"));
        tShirt.setQuantity(1);

        tShirt = tShirtDao.addTshirt(tShirt);

        List<Tshirt> tShirtList = tShirtDao.getTshirtByColor("Yellow");
        assertEquals(2, tShirtList.size());
    }

    @Test
    public void getTshirtBySize() {
        Tshirt tShirt = new Tshirt();
        tShirt.setSize("m");
        tShirt.setColor("Orange");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("L");
        tShirt.setColor("Yellow");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("17.99"));
        tShirt.setQuantity(3);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("Medium");
        tShirt.setColor("white");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("18.99"));
        tShirt.setQuantity(2);

        tShirt = tShirtDao.addTshirt(tShirt);

        tShirt = new Tshirt();
        tShirt.setSize("m");
        tShirt.setColor("Yellow");
        tShirt.setDescription("its a shirt");
        tShirt.setPrice(new BigDecimal("17.99"));
        tShirt.setQuantity(1);

        tShirt = tShirtDao.addTshirt(tShirt);

        List<Tshirt> tShirtList = tShirtDao.getTshirtBySize("m");
        assertEquals(2, tShirtList.size());
    }
}