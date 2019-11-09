package com.company.gamestoreservice.service;


import com.company.gamestoreservice.dao.*;
import com.company.gamestoreservice.dto.*;
import com.company.gamestoreservice.exception.InputValidationException;
import com.company.gamestoreservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class GameStoreServiceLayer {
    ConsoleDao consoleDao;
    GameDao gameDao;
    InvoiceDao invoiceDao;
    ProcessingFeeDao processingFeeDao;
    SalesTaxRateDao salesTaxRateDao;
    TshirtDao tShirtDao;


    @Autowired
    public GameStoreServiceLayer(ConsoleDao consoleDao, GameDao gameDao, InvoiceDao invoiceDao, ProcessingFeeDao processingFeeDao, SalesTaxRateDao salesTaxRateDao, TshirtDao tShirtDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.invoiceDao = invoiceDao;
        this.processingFeeDao = processingFeeDao;
        this.salesTaxRateDao = salesTaxRateDao;
        this.tShirtDao = tShirtDao;
    }
// console api
    public Console saveConsole(Console console){
            return consoleDao.addConsole(console);
    }

    public Console findConsole(int id){
        return consoleDao.getConsole(id);
    }

    public List<Console> findAllConsoles(){
        return consoleDao.getAllConsoles();
    }

    public Console updateConsole(Console console){
        return consoleDao.updateConsole(console);
    }

    public void removeCosnole(int id){
         consoleDao.deleteConsole(id);
    }

    public List<Console> findAllByManufacturer(String manufacturer){
        return consoleDao.getConsoleByManufacturer(manufacturer);
    }

    // game api

    public Game saveGame(Game game){
        return gameDao.addGame(game);
    }

    public Game findGame(int id){
        return gameDao.getGame(id);
    }

    public List<Game> findAllGames(){
        return gameDao.getAllGames();
    }

    public Game updateGame(Game game){
        return gameDao.updateGame(game);
    }

    public void removeGame(int id){
        gameDao.deleteGame(id);
    }

    public List<Game> findGamesByStudio(String studio){
        return gameDao.getGamesByStudio(studio);
    }

    public List<Game> findGamesByTitle(String title){
        return gameDao.getGamesByTitle(title);
    }

    public List<Game> findGamesByEsrbRating(String esrb){
        return gameDao.getGamesByEsrbRating(esrb);
    }

    // tshirt api

    public Tshirt saveTshirt(Tshirt tshirt){
        return tShirtDao.addTshirt(tshirt);
    }

    public Tshirt findTshirt(int id){
        return tShirtDao.getTshirt(id);
    }
    public List<Tshirt> findAllTshirts(){
        return tShirtDao.getAllTshirts();
    }

    public Tshirt updateTshirt(Tshirt tshirt){
        return tShirtDao.updateTshirt(tshirt);
    }
    public void deleteTshirt(int id){
         tShirtDao.deleteTshirt(id);
    }

    public List<Tshirt> findTshirtByColor(String color){
        return tShirtDao.getTshirtByColor(color);
    }

    public List<Tshirt> findTshirtBySize(String size){
        return tShirtDao.getTshirtBySize(size);
    }

    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) throws InputValidationException{
        String errors = inputValidation(invoiceViewModel);
        if(!errors.equals("none")){
            throw new InputValidationException(errors);
        }

        Invoice invoice = new Invoice();

        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipCode(invoiceViewModel.getZipCode());
        invoice.setItemType(invoiceViewModel.getItemType());
        invoice.setItemId(invoiceViewModel.getItemId());
        invoice.setQuantity(invoiceViewModel.getQuantity());


        BigDecimal unitPrice = findUnitPrice(invoiceViewModel.getItemType(), invoiceViewModel.getItemId());
        invoice.setUnitPrice(unitPrice);


        BigDecimal subTotal = invoice.getUnitPrice().multiply(new BigDecimal(invoice.getQuantity()));
        invoice.setSubTotal(subTotal);


        SalesTaxRate salesTaxRate = salesTaxRateDao.getSalesTaxRate("NJ");
        BigDecimal taxRate = salesTaxRate.getRate();
        invoice.setTax(taxRate);


        BigDecimal processingFee = findProcessingFee(invoice.getQuantity(), invoice.getItemType());
        invoice.setProcessingFee(processingFee);


        BigDecimal total = subTotal.add(subTotal.multiply(invoice.getTax()).setScale(2, RoundingMode.HALF_UP));

        total = total.add(processingFee).setScale(2, RoundingMode.HALF_UP);
        invoice.setTotal(total);

        invoice = invoiceDao.addInvoice(invoice);

        calculateStock(invoice, invoice.getItemId());

        return buildInvoiceViewModel(invoice);
    }

    public InvoiceViewModel findInvoice(int id) {
        Invoice invoice = invoiceDao.getInvoice(id);
        if (invoice == null)
            return null;
        else
            return buildInvoiceViewModel(invoice);
    }

    public List<Invoice> findAllInvoices() {

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        return invoiceList;

    }


    // make the build methods

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setName(invoice.getName());
        invoiceViewModel.setStreet(invoice.getStreet());
        invoiceViewModel.setCity(invoice.getCity());
        invoiceViewModel.setState(invoice.getState());
        invoiceViewModel.setZipCode(invoice.getZipCode());
        invoiceViewModel.setItemType(invoice.getItemType());
        invoiceViewModel.setItemId(invoice.getItemId());
        invoiceViewModel.setQuantity(invoice.getQuantity());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setSubTotal(invoice.getSubTotal());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTotal(invoice.getTotal());

        return invoiceViewModel;
    }

    Boolean isItemValid(String itemType) {
        switch (itemType.toLowerCase()) {
            case "game":
            case "games":
                return true;

            case "t_shirt":
            case "t_shirts":
                return true;

            case "console":
            case "consoles":
                return true;

            default:
                return false;
        }
    }

    Boolean validStateCode(String state){
        SalesTaxRate salesTaxRate= salesTaxRateDao.getSalesTaxRate(state);
        if (salesTaxRate == null) return false;
        else return true;

    }

    private BigDecimal findProcessingFee(int quantity, String productType) {
        ProcessingFee processingFee = processingFeeDao.getProcessingFee(productType);

        if (processingFee == null) {
            return null;
        } else {
            BigDecimal fee = processingFee.getFee().setScale(2, RoundingMode.HALF_UP);
            if (quantity > 10) {
                fee = fee.add(new BigDecimal("15.49").setScale(2, RoundingMode.HALF_UP));
            }
            return fee;
        }
    }

    private Integer checkAvailability(int itemId, String productType) {

        int stock = 0;

        switch (productType) {
            case "game":
            case "games":
                Game game = gameDao.getGame(itemId);
                if (game == null)
                    return null;
                else stock = game.getQuantity();
                break;
            case "t_shirt":
            case "t_shirts":
                Tshirt tShirt = tShirtDao.getTshirt(itemId);
                if (tShirt == null)
                    return null;
                else stock = tShirt.getQuantity();
                break;
            case "console":
            case "consoles":
                Console console = consoleDao.getConsole(itemId);
                if (console == null)
                    return null;
                else stock = console.getQuantity();
                break;
            default:
                stock = 0;
        }
        return stock;
    }


    public void calculateStock(Invoice invoice, int itemId){

        int orderQuantity = invoice.getQuantity();
        int stock = checkAvailability(invoice.getItemId(), invoice.getItemType());

        stock = stock-orderQuantity;
        updateInventory(invoice.getItemType(), invoice.getItemId(), stock);
    }


    public void updateInventory(String productType, int itemId, int quantity){

        switch (productType) {
            case "game":
            case "games":
                Game game = gameDao.getGame(itemId);
                game.setQuantity(quantity);
                gameDao.updateGame(game);
                break;
            case "t_shirt":
            case "t_shirts":
                Tshirt tShirt = tShirtDao.getTshirt(itemId);
                tShirt.setQuantity(quantity);
                tShirtDao.updateTshirt(tShirt);
                break;
            case "console":
            case "consoles":
                Console console = consoleDao.getConsole(itemId);
                console.setQuantity(quantity);
                consoleDao.updateConsole(console);
                break;
            default:
                return;
        }

    }


    private BigDecimal findUnitPrice(String itemType, int itemId) {

        BigDecimal unitPrice = new BigDecimal("0.00");

        switch (itemType.toLowerCase()) {
            case "game":
            case "games":
                Game game = gameDao.getGame(itemId);
                if (game == null)

                    return null;
                else unitPrice = game.getPrice();
                break;

            case "t_shirt":
            case "t_shirts":
                Tshirt tShirt = tShirtDao.getTshirt(itemId);
                if (tShirt == null) return null;
                else unitPrice = tShirt.getPrice();
                break;

            case "console":
            case "consoles":
                Console console = consoleDao.getConsole(itemId);
                if (console == null) return null;
                else unitPrice = console.getPrice();
                unitPrice = console.getPrice();
                break;
            default:
                System.out.println("Item not found");
        }
        return unitPrice;
    }



    public String inputValidation(InvoiceViewModel invoiceViewModel) throws InputValidationException{
        String error = "none";

        Boolean checkState = validStateCode(invoiceViewModel.getState());
        Boolean itemValid = isItemValid(invoiceViewModel.getItemType());
        int stockInHand = checkAvailability(invoiceViewModel.getItemId(), invoiceViewModel.getItemType());

        if (checkState == false) {
            error = "Invalid state code.";
        } else if (!itemValid) {
            error = "Invalid product type.";
        } else if (invoiceViewModel.getQuantity() > stockInHand) {
            error = "Insufficient stock.";
        }
        return error;
    }






}

