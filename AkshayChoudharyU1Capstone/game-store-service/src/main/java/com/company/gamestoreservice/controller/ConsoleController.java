package com.company.gamestoreservice.controller;


import com.company.gamestoreservice.dto.Console;
import com.company.gamestoreservice.exception.NotFoundException;
import com.company.gamestoreservice.service.GameStoreServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class ConsoleController {

    @Autowired
    GameStoreServiceLayer service;

    @PostMapping("/console")
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody @Valid Console console) {
        return service.saveConsole(console);
    }

    @GetMapping("/console/getId/{consoleId}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsole(@PathVariable("consoleId") int consoleId) {
        Console console = service.findConsole(consoleId);
        if (console == null)
            throw new NotFoundException("Console could not be retrieved for id " + consoleId);
        return console;
    }

    @DeleteMapping("/console/delete/{consoleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable("consoleId") int consoleId) {
        service.removeCosnole(consoleId);
    }

    @PutMapping("/console/update/{consoleId}")
    @ResponseStatus(HttpStatus.OK)
    public Console updateConsole(@PathVariable("consoleId") int consoleId, @RequestBody @Valid Console console) {
        if (console.getConsoleId() == 0)
            console.setConsoleId(consoleId);
        if (consoleId != console.getConsoleId()) {
            throw new IllegalArgumentException("Console ID on path must match the ID in the Console object");
        }
        console = service.updateConsole(console);
        return console;
    }

    @GetMapping("/console/getManufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsoleByManufacturer(@PathVariable(value = "manufacturer") String manufacturer) {
        List<Console> console = service.findAllByManufacturer(manufacturer);
        if (console.size() == 0)
            throw new NotFoundException("No console found by " + manufacturer);
        return console;
    }
}
