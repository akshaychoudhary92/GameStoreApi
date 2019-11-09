package com.company.gamestoreservice.service;

import com.company.gamestoreservice.dao.*;
import com.company.gamestoreservice.dto.*;
import com.company.gamestoreservice.exception.InputValidationException;
import com.company.gamestoreservice.viewModel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GameStoreServiceLayerTest {

    ConsoleDao consoleDao;
    GameDao gameDao;
    InvoiceDao invoiceDao;
    ProcessingFeeDao processingFeeDao;
    SalesTaxRateDao salesTaxRateDao;
    TshirtDao tShirtDao;
    GameStoreServiceLayer service;


    @Before
    public void setUp() throws Exception {

        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpInvoiceDaoMock();
        setUpTShirtDaoMock();
        setUpSalesTaxDaoMock();
        setUpProcessingFeeDaoMock();


        service = new GameStoreServiceLayer(consoleDao, gameDao, invoiceDao, processingFeeDao, salesTaxRateDao, tShirtDao);
    }

    private void setUpConsoleDaoMock(){

        consoleDao = mock(ConsoleDaoJdbcTemplateImpl.class);

        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("testmodel");
        console.setManufacturer("wii");
        console.setMemoryAmount("3gb");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("100.12").setScale(2, RoundingMode.HALF_UP));
        console.setQuantity(3);

        Console console1 = new Console();
        console1.setModel("testmodel");
        console1.setManufacturer("wii");
        console1.setMemoryAmount("3gb");
        console1.setProcessor("Processor");
        console1.setPrice(new BigDecimal("100.12").setScale(2, RoundingMode.HALF_UP));
        console1.setQuantity(3);

        List<Console> consoleList = new ArrayList<>();
        consoleList.add(console);

        doReturn(console).when(consoleDao).addConsole(console1);
        doReturn(console).when(consoleDao).getConsole(1);
        doReturn(consoleList).when(consoleDao).getAllConsoles();
        doReturn(consoleList).when(consoleDao).getConsoleByManufacturer("wii");
    }

    private void setUpGameDaoMock(){

        gameDao = mock(GameDaoJdbcTemplateImpl.class);

        Game game = new Game();
        game.setGameId(1);
        game.setTitle("Minecraft");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("This is an adventure game.");
        game.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Microsoft");
        game.setQuantity(2);

        Game game1 = new Game();
        game1.setTitle("Minecraft");
        game1.setEsrbRating("Everyone 10+");
        game1.setDescription("This is an adventure game.");
        game1.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game1.setStudio("Microsoft");
        game1.setQuantity(2);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);

        doReturn(game).when(gameDao).addGame(game1);
        doReturn(game).when(gameDao).getGame(1);
        doReturn(gameList).when(gameDao).getAllGames();
        doReturn(gameList).when(gameDao).getGamesByStudio("Microsoft");
        doReturn(gameList).when(gameDao).getGamesByEsrbRating("Everyone 10+");
        doReturn(gameList).when(gameDao).getGamesByTitle("Minecraft");
    }

    private void setUpTShirtDaoMock(){

        tShirtDao = mock(TshirtDaoJdbcTemplateImpl.class);

        Tshirt tShirt = new Tshirt();

        tShirt.settShirtId(1);
        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("Cool T-Shirt");
        tShirt.setPrice(new BigDecimal("15.99").setScale(2, RoundingMode.HALF_UP));
        tShirt.setQuantity(1);

        Tshirt tShirt1 = new Tshirt();

        tShirt1.setSize("M");
        tShirt1.setColor("Red");
        tShirt1.setDescription("Cool T-Shirt");
        tShirt1.setPrice(new BigDecimal("15.99").setScale(2, RoundingMode.HALF_UP));
        tShirt1.setQuantity(1);

        List<Tshirt> tShirtList = new ArrayList<>();
        tShirtList.add(tShirt);

        doReturn(tShirt).when(tShirtDao).addTshirt(tShirt1);
        doReturn(tShirt).when(tShirtDao).getTshirt(1);
        doReturn(tShirtList).when(tShirtDao).getAllTshirts();
        doReturn(tShirtList).when(tShirtDao).getTshirtByColor("Red");
        doReturn(tShirtList).when(tShirtDao).getTshirtBySize("M");
    }

    private void setUpInvoiceDaoMock(){

        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("Akshay");
        invoice.setStreet("test");
        invoice.setCity("Atlanta");
        invoice.setState("GA");
        invoice.setZipCode("30098");
        invoice.setItemType("consoles");
        invoice.setItemId(1);
        invoice.setUnitPrice(new BigDecimal("100.12"));
        invoice.setQuantity(3);
        invoice.setSubTotal(new BigDecimal("607.22"));
        invoice.setTax(new BigDecimal("0.05").setScale(2, RoundingMode.HALF_UP));
        invoice.setProcessingFee(new BigDecimal("34.99").setScale(2, RoundingMode.HALF_UP));
        invoice.setTotal(new BigDecimal("331.12").setScale(2,RoundingMode.HALF_UP));

        Invoice invoice1 = new Invoice();

        invoice1.setName("Akshay");
        invoice1.setStreet("test");
        invoice1.setCity("Atlanta");
        invoice1.setState("GA");
        invoice1.setZipCode("30098");
        invoice1.setItemType("consoles");
        invoice1.setItemId(1);
        invoice1.setUnitPrice(new BigDecimal("100.12"));
        invoice1.setQuantity(3);
        invoice1.setSubTotal(new BigDecimal("607.22"));
        invoice1.setTax(new BigDecimal("0.05").setScale(2, RoundingMode.HALF_UP));
        invoice1.setProcessingFee(new BigDecimal("34.99").setScale(2, RoundingMode.HALF_UP));
        invoice1.setTotal(new BigDecimal("331.12").setScale(2,RoundingMode.HALF_UP));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoice(1);
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();

    }

    private void setUpSalesTaxDaoMock(){
        salesTaxRateDao = mock(SalesTaxRateDaoTemplateImpl.class);

        SalesTaxRate salesTaxRate = new SalesTaxRate();
        salesTaxRate.setState("GA");
        salesTaxRate.setRate(new BigDecimal("0.05").setScale(2,RoundingMode.HALF_UP));

        SalesTaxRate salesTaxRate1 = new SalesTaxRate();
        salesTaxRate1.setState("GA");
        salesTaxRate1.setRate(new BigDecimal("0.05").setScale(2,RoundingMode.HALF_UP));

        doReturn(salesTaxRate).when(salesTaxRateDao).getSalesTaxRate(salesTaxRate1.getState());
    }

    private void setUpProcessingFeeDaoMock(){
        processingFeeDao = mock(ProcessingFeeDaoJdbcTemplateImpl.class);

        ProcessingFee processingFee = new ProcessingFee();
        processingFee.setProductType("consoles");
        processingFee.setFee(new BigDecimal("34.99").setScale(2, RoundingMode.HALF_UP));

        ProcessingFee processingFee1 = new ProcessingFee();
        processingFee1.setProductType("consoles");
        processingFee1.setFee(new BigDecimal("34.99").setScale(2, RoundingMode.HALF_UP));

        doReturn(processingFee).when(processingFeeDao).getProcessingFee(processingFee.getProductType());
    }

    @Test
    public void saveFindConsole() {
        Console console = new Console();

        console.setModel("testmodel");
        console.setManufacturer("wii");
        console.setMemoryAmount("3gb");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("100.12").setScale(2, RoundingMode.HALF_UP));
        console.setQuantity(3);

        console = service.saveConsole(console);
        Console fromService = service.findConsole(console.getConsoleId());
        assertEquals(console, fromService);
    }


    @Test
    public void findAllConsoles() {
        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("testmodel");
        console.setManufacturer("wii");
        console.setMemoryAmount("3gb");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("100.12").setScale(2, RoundingMode.HALF_UP));
        console.setQuantity(3);

        console = service.saveConsole(console);
        List<Console> fromService = service.findAllByManufacturer("wii");
        assertEquals(1, fromService.size());
    }





    @Test
    public void saveFindGame() {
        Game game = new Game();
        game.setTitle("Minecraft");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("This is an adventure game.");
        game.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Microsoft");
        game.setQuantity(2);

        game = service.saveGame(game);
        Game fromService = service.findGame(game.getGameId());
        assertEquals(game, fromService);
    }


    @Test
    public void findGamesByStudio() {
        Game game = new Game();
        game.setGameId(1);
        game.setTitle("Minecraft");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("This is an adventure game.");
        game.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Microsoft");
        game.setQuantity(2);

        game = service.saveGame(game);
        List<Game> fromService = service.findGamesByStudio("Microsoft");
        assertEquals(1, fromService.size());

    }

    @Test
    public void findAllGames() {

        Game game = new Game();

        game.setGameId(1);
        game.setTitle("Fortnite");
        game.setEsrbRating("EC");
        game.setDescription("fortnite@gmail.com");
        game.setPrice(new BigDecimal(49.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Hollywood");
        game.setQuantity(12);

        List<Game> gameViewModelList = service.findAllGames();
        assertEquals(gameViewModelList.size(),1);
    }

    @Test
    public void findGamesByTitle() {
        Game game = new Game();
        game.setGameId(1);
        game.setTitle("Minecraft");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("This is an adventure game.");
        game.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Microsoft");
        game.setQuantity(2);

        game = service.saveGame(game);
        List<Game> fromService = service.findGamesByTitle("Minecraft");
        assertEquals(1, fromService.size());
    }

    @Test
    public void findGamesByEsrbRating() {
        Game game = new Game();
        game.setGameId(1);
        game.setTitle("Minecraft");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("This is an adventure game.");
        game.setPrice(new BigDecimal(59.99).setScale(2, RoundingMode.HALF_UP));
        game.setStudio("Microsoft");
        game.setQuantity(2);

        game = service.saveGame(game);
        List<Game> fromService = service.findGamesByEsrbRating("Everyone 10+");
        assertEquals(1, fromService.size());
    }

    @Test
    public void saveFindTshirt() {
        Tshirt tShirt = new Tshirt();

        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("Cool T-Shirt");
        tShirt.setPrice(new BigDecimal("15.99").setScale(2,RoundingMode.HALF_UP));
        tShirt.setQuantity(1);
        tShirt = service.saveTshirt(tShirt);

        Tshirt fromService = service.findTshirt(tShirt.gettShirtId());
        assertEquals(fromService, tShirt);

    }



    @Test
    public void findAllTshirts() {
        Tshirt tshirt = new Tshirt();

        tshirt.settShirtId(1);
        tshirt.setSize("Large");
        tshirt.setColor("Blue");
        tshirt.setDescription("Sportswear");
        tshirt.setPrice(new BigDecimal(30).setScale(2, RoundingMode.HALF_UP));
        tshirt.setQuantity(10);

        List<Tshirt> tshirtViewModelList = service.findAllTshirts();
        assertEquals(tshirtViewModelList.size(),1);
    }


    @Test
    public void findTshirtByColor() {
        Tshirt tShirt = new Tshirt();

        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("Cool T-Shirt");
        tShirt.setPrice(new BigDecimal("15.99").setScale(2,RoundingMode.HALF_UP));
        tShirt.setQuantity(1);
        tShirt = service.saveTshirt(tShirt);

        List<Tshirt> fromService = service.findTshirtByColor("Red");
        assertEquals(1,fromService.size());
    }

    @Test
    public void findTshirtBySize() {
        Tshirt tShirt = new Tshirt();

        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("Cool T-Shirt");
        tShirt.setPrice(new BigDecimal("15.99").setScale(2,RoundingMode.HALF_UP));
        tShirt.setQuantity(1);
        tShirt = service.saveTshirt(tShirt);

        List<Tshirt> fromService = service.findTshirtBySize("M");
        assertEquals(1,fromService.size());

    }


    @Test
    public void SaveInvoice() throws InputValidationException {
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setName("Akshay");
        invoice.setStreet("testroad");
        invoice.setCity("atlanta");
        invoice.setState("GA");
        invoice.setZipCode("30039");
        invoice.setItemType("consoles");
        invoice.setItemId(1);
        invoice.setUnitPrice(new BigDecimal("100.36").setScale(2, RoundingMode.HALF_UP));
        invoice.setQuantity(3);
        invoice.setSubTotal(new BigDecimal("301.08").setScale(2, RoundingMode.HALF_UP));
        invoice.setTax(new BigDecimal("0.05").setScale(2, RoundingMode.HALF_UP));
        invoice.setProcessingFee(new BigDecimal("14.99").setScale(2, RoundingMode.HALF_UP));
        invoice.setTotal(new BigDecimal("330.56").setScale(2,RoundingMode.HALF_UP));
        invoice = service.saveInvoice(invoice);

    }






}