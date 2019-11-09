package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Console;

import java.util.List;

public interface ConsoleDao {
    Console addConsole(Console console);

    Console getConsole(int Id);

    List<Console> getAllConsoles();

    Console updateConsole(Console console);

    void deleteConsole(int id);

    List<Console> getConsoleByManufacturer(String manufacturer);
}
