package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Tshirt;

import java.util.List;

public interface TshirtDao {
    Tshirt addTshirt(Tshirt tShirt);

    Tshirt getTshirt(int Id);

    List<Tshirt> getAllTshirts();

    Tshirt updateTshirt(Tshirt tShirt);

    void deleteTshirt(int id);

    List<Tshirt> getTshirtByColor(String color);

    List<Tshirt> getTshirtBySize(String size);

}
