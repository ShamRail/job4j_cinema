package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Place;

import java.util.List;

public interface CinemaService {

    void buy(Account account, Place place);
    List<Place> findAllPlaces();

}
