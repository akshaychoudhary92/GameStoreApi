package com.company.gamestoreservice.controller;


import com.company.gamestoreservice.dto.Tshirt;
import com.company.gamestoreservice.exception.NotFoundException;
import com.company.gamestoreservice.service.GameStoreServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tshirt")
public class TshirtController {

    @Autowired
    GameStoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addTShirt(@RequestBody @Valid Tshirt tshirt) {
        return service.saveTshirt(tshirt);
    }

    @GetMapping("/{tshirtId}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirt(@PathVariable("tshirtId") int tshirtId) {
        Tshirt tshirt= service.findTshirt(tshirtId);
        if (tshirt== null)
            throw new NotFoundException("Tshirt could not be retrieved for id " + tshirtId);
        return tshirt;
    }

    @DeleteMapping("/{tshirtId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable("tshirtId") int tshirtId) {
        service.deleteTshirt(tshirtId);
    }

    @PutMapping("/{tshirtId}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt updateTShirt(@PathVariable("tshirtId") int tshirtId, @RequestBody @Valid Tshirt tshirt ){
        if (tshirt.gettShirtId() == 0)
            tshirt.settShirtId(tshirtId);
        if (tshirtId != tshirt.gettShirtId()) {
            throw new IllegalArgumentException("T-Shirt ID on path must match the ID in the T-Shirt object");
        }
        return service.updateTshirt(tshirt);
    }

    @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getTshirtWithColor(@PathVariable("color") String color) {
        List<Tshirt> tshirt = service.findTshirtByColor(color);
        if (tshirt.size() == 0)
            throw new NotFoundException("No " + color + " tshirts available.");
        return tshirt;
    }

    @GetMapping("/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getTshirtWithSize(@PathVariable("size") String size) {
        List<Tshirt> tshirt = service.findTshirtBySize(size);
        if (tshirt.size() == 0)
            throw new NotFoundException("No " + size + " tshirts available.");
        return tshirt;
    }

}
