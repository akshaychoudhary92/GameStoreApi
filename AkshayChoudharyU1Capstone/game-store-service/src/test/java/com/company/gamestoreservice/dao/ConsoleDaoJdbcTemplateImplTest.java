package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Console;
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
public class ConsoleDaoJdbcTemplateImplTest {

    @Autowired
    private ConsoleDao consoleDao;


    @Before
    public void setUp() throws Exception {
        List<Console> consolesList = consoleDao.getAllConsoles();

        consolesList.stream()
                .forEach(console -> consoleDao.deleteConsole(console.getConsoleId()));
    }


   @Test
   public void addGetDeleteConsole(){
       Console console = new Console();
       console.setModel("testmodel");
       console.setManufacturer("testManufacturer");
       console.setMemoryAmount("blah");
       console.setProcessor("testestestess");
       console.setPrice(new BigDecimal("5.87"));
       console.setQuantity(9);

       console = consoleDao.addConsole(console);
       Console console1 = consoleDao.getConsole(console.getConsoleId());

       assertEquals(console, console1);
       consoleDao.deleteConsole(console.getConsoleId());
       console1 =consoleDao.getConsole(console.getConsoleId());

       assertNull(console1);
   }



    @Test
    public void getAllConsoles() {
        Console console = new Console();
        console.setModel("testmodel");
        console.setManufacturer("testManufacturer");
        console.setMemoryAmount("blah");
        console.setProcessor("testestestess");
        console.setPrice(new BigDecimal("5.87"));
        console.setQuantity(9);

        console = consoleDao.addConsole(console);

        console = new Console();
        console.setModel("hally");
        console.setManufacturer("cray");
        console.setMemoryAmount("booboo");
        console.setProcessor("fastwroom");
        console.setPrice(new BigDecimal("97.90"));
        console.setQuantity(9);

        console = consoleDao.addConsole(console);

        List<Console> consoles = consoleDao.getAllConsoles();

        assertEquals(2, consoles.size());
    }

    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setModel("testmodel");
        console.setManufacturer("testManufacturer");
        console.setMemoryAmount("blah");
        console.setProcessor("testestestess");
        console.setPrice(new BigDecimal("5.87"));
        console.setQuantity(9);

        console = consoleDao.addConsole(console);

        console.setModel("Wii1");
        console.setManufacturer("testmanu1");
        console.setMemoryAmount("howdyco");
        console.setProcessor("speedyProcessor1");
        console.setPrice(new BigDecimal("45.78"));
        console.setQuantity(1);

        consoleDao.updateConsole(console);

        Console console1 = consoleDao.getConsole(console.getConsoleId());

        assertEquals(console1, console);
    }



    @Test
    public void getConsoleByManufacturer() {
        Console console = new Console();
        console.setModel("testmodel");
        console.setManufacturer("SomeBigManufacturer");
        console.setMemoryAmount("blah");
        console.setProcessor("testestestess");
        console.setPrice(new BigDecimal("5.87"));
        console.setQuantity(9);


        console = consoleDao.addConsole(console);

        console = new Console();
        console.setModel("newNintendo");
        console.setManufacturer("SomeBigManufacturer");
        console.setMemoryAmount("smallGB");
        console.setProcessor("slowProcessor");
        console.setPrice(new BigDecimal("25.99"));
        console.setQuantity(3);

        console = consoleDao.addConsole(console);

        console = new Console();
        console.setModel("Wii1");
        console.setManufacturer("Sega");
        console.setMemoryAmount("howdyco");
        console.setProcessor("speedyProcessor1");
        console.setPrice(new BigDecimal("45.78"));
        console.setQuantity(1);

        console = consoleDao.addConsole(console);

        List<Console> consoleList = consoleDao.getConsoleByManufacturer("SomeBigManufacturer");
        assertEquals(2, consoleList.size());

        List<Console> consoleList1 = consoleDao.getConsoleByManufacturer("Sega");
        assertEquals(1, consoleList1.size());
    }
}