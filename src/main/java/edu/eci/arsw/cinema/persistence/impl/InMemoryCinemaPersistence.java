/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service("EnMemoria")
public class InMemoryCinemaPersistence implements CinemaPersitence {

    private final ConcurrentHashMap<String, Cinema> cinemas = new ConcurrentHashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate1 = "2018-12-18 15:30";
        String functionDate2 = "2018-12-18 17:00";
        String functionDate3 = "2018-12-18 10:00";
        CopyOnWriteArrayList<CinemaFunction> functions1 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CinemaFunction> functions2 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CinemaFunction> functions3 = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate1);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate1);
        CinemaFunction funct3 = new CinemaFunction(new Movie("Coraline", "Horror"), functionDate2);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Inception", "Suspense"), functionDate2);
        CinemaFunction funct5 = new CinemaFunction(new Movie("Shrek", "Comedy"), functionDate3);
        CinemaFunction funct6 = new CinemaFunction(new Movie("Shrek 2", "Comedy"), functionDate3);
        CinemaFunction funct7 = new CinemaFunction(new Movie("The Enigma", "Drama"), functionDate1);
        CinemaFunction funct8 = new CinemaFunction(new Movie("The Enigma 2", "Drama"), functionDate1);

        functions1.add(funct1);
        functions1.add(funct2);
        functions2.add(funct3);
        functions2.add(funct4);
        functions3.add(funct5);
        functions3.add(funct6);
        functions1.add(funct7);
        functions1.add(funct8);

        Cinema c1 = new Cinema("cinemaX", functions1);
        Cinema c2 = new Cinema("Cine", functions2);
        Cinema c3 = new Cinema("SuperCine", functions3);

        cinemas.put("cinemaX", c1);
        cinemas.put("Cine", c2);
        cinemas.put("SuperCine", c3);
    }

    /*La fila y columna no pueden menores de 1, y no pueden estar vacio el cinema, 
    la fecha y el nombre de la pelicula*/
    @Override
    @SuppressWarnings("empty-statement")
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException {
        if ((row <= 0) || col <= 0) {
            throw new CinemaPersistenceException("Fila invalida o clumna inválidas");
        };
        if (cinema == null) {
            throw new CinemaPersistenceException("El nombre del cine no puede estar vacio");
        };
        if (date == null) {
            throw new CinemaPersistenceException("La fecha no puede estar vacia");
        };
        if (movieName == null) {
            throw new CinemaPersistenceException("El nombre de la pelicula no puede estar vacio");
        };
        Cinema cine = getCinemaByName(cinema);
        if (cine == null) {
            throw new CinemaPersistenceException("Este cinema no existe");
        }

        //la funcion debe existir para poder hacer la compra
        try {
            CinemaFunction funcion = cine.existeFuncion(movieName, date);
            funcion.buyTicket(row, col);
        } catch (CinemaException ex) {
            Logger.getLogger(InMemoryCinemaPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //La fecha y el cine a buscar no pueden estar vacios. Y el cine debe existir.
    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException {

        if (cinema == null) {
            throw new CinemaPersistenceException("El cinema a buscar no puede estar vacio");
        }

        if (date == null) {
            throw new CinemaPersistenceException("La fecha a buscar no puede estar vacia");
        }

        Cinema cine = getCinemaByName(cinema);
        if (cine == null) {
            throw new CinemaPersistenceException("El cinema que busca no existe");
        }

        try {
            return cine.getFunctionsDate(date);
        } catch (CinemaException ex) {
            throw new CinemaPersistenceException(ex.getMessage(), ex);
        }
    }

    //La fecha y el cine a buscar no pueden estar vacios. Y el cine debe existir.
    @Override
    public CinemaFunction getFunctionbyCinemaAndDateAndMovie (String cinema, String date, String moviename) throws CinemaPersistenceException {

        if (cinema == null) {
            throw new CinemaPersistenceException("El cinema a buscar no puede estar vacio");
        }

        if (date == null) {
            throw new CinemaPersistenceException("La fecha a buscar no puede estar vacia");
        }

        if (moviename == null) {
            throw new CinemaPersistenceException("La funcionn a buscar no puede estar vacia");
        }

        Cinema cine = getCinemaByName(cinema);
        if (cine == null) {
            throw new CinemaPersistenceException("El cinema que busca no existe");
        }

        try {
            return cine.getFunctionDateAndMovie(date, moviename);
        } catch (CinemaException ex) {
            throw new CinemaPersistenceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteFunctionByCinemaAndFunction(Cinema name, CinemaFunction function) throws CinemaPersistenceException {
        if (!cinemas.containsKey(name.getName())) {
            throw new CinemaPersistenceException("El cinema no existe");
        }
        boolean deleted = false;
        for (CinemaFunction cFunction : name.getFunctions()) {
            if (cFunction.getMovie().getName().equals(function.getMovie().getName())) {
                name.deleteFunction(function);
                deleted = true;
            }
        }
        if (!deleted) {
            throw new CinemaPersistenceException("La pelicula no pertenece a ese cinema");
        }
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())) {
            throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
        } else {
            cinemas.put(c.getName(), c);
        }
    }

    //El nombre del cine a buscar no puede estar vacio y debe existir 
    @Override
    public Cinema getCinemaByName(String name) throws CinemaPersistenceException {
        if (name == null) {
            throw new CinemaPersistenceException("El nombre del cinema a buscar no puede estar vacio");
        }
        if (!cinemas.containsKey(name)) {
            throw new CinemaPersistenceException("El cinema que busca no existe");
        }
        return cinemas.get(name);
    }

    //Para devolver el set que nos piden miramos en la libreria de hash un tipo set
    @Override
    public Set<Cinema> getCinemas() {
        return new HashSet(cinemas.values());
    }

    @Override
    public List<CinemaFunction> getFunctionByName(String cinema, String date, String moviename) throws CinemaPersistenceException {
        if (cinema == null) {
            throw new CinemaPersistenceException("El nombre del cinema a buscar no puede estar vacio");
        }
        if (!cinemas.containsKey(cinema)) {
            throw new CinemaPersistenceException("El cinema que busca no existe");
        }
        if (date == null) {
            throw new CinemaPersistenceException("No hay funciones en esta fecha");
        }
        if (moviename == null) {
            throw new CinemaPersistenceException("El nombre de la pelicula a buscar no puede estar vacio");
        }
        Cinema cine = getCinemaByName(cinema);
        try {
            return cine.getFunctionByName(moviename);
        } catch (CinemaException ex) {
            throw new CinemaPersistenceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void addFunctionByName(String cinema, CinemaFunction function) throws CinemaPersistenceException {
        if (cinema == null || function == null) {
            throw new CinemaPersistenceException("El nombre del cinema a buscar o la función no pueden estar vacíos");
        }
        Cinema cine = getCinemaByName(cinema);
        try {
            cine.addNewFunction(function);
        } catch (CinemaException ex) {
            throw new CinemaPersistenceException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void updateFunctionByName(String cinema, CinemaFunction function) throws CinemaPersistenceException {
        if (cinema == null || function == null) {
            throw new CinemaPersistenceException("El nombre del cinema a buscar o la función no pueden estar vacíos");
        }
        Cinema cine = getCinemaByName(cinema);
        try {
            cine.updateFunction(function);
        } catch (CinemaException ex) {
            throw new CinemaPersistenceException(ex.getMessage(), ex);
        }
    }
}
