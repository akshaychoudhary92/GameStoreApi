package com.company.gamestoreservice.controller;

import com.company.gamestoreservice.exception.InputValidationException;
import com.company.gamestoreservice.exception.NotFoundException;
import com.company.gamestoreservice.service.GameStoreServiceLayer;
import com.company.gamestoreservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class InvoiceController {

    @Autowired
    GameStoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel addInvoice(@RequestBody @Valid InvoiceViewModel inputInvoiceViewModel) throws InputValidationException {
        InvoiceViewModel outputInvoiceViewModel = service.saveInvoice(inputInvoiceViewModel);

        if (outputInvoiceViewModel  == null)
            throw new InputValidationException("Invoice could not be created.");
        return outputInvoiceViewModel ;
    }

    @GetMapping("/invoice/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getGame(@PathVariable("invoiceId") int invoiceId) {
        InvoiceViewModel invoiceViewModel = service.findInvoice(invoiceId);
        if (invoiceViewModel == null)
            throw new NotFoundException("Invoice could not be retrieved for id " + invoiceId);
        return invoiceViewModel;
    }
}
