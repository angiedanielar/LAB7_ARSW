/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.*;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/cinema")
public class CinemaAPIController {

    @Autowired
    @Qualifier("ServiciosCine")
    private CinemaServices cinemaServices = null;

    //Métodos GET
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas() {
        try {
            return new ResponseEntity<>(cinemaServices.getAllCinemas(), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error, no hay cinema", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getCinemaByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(cinemaServices.getCinemaByName(name), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No existe el cine especificado", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{name}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionsByCinemaAndDate(@PathVariable String name, @PathVariable String date) {
        try {
            return new ResponseEntity<>(cinemaServices.getFunctionsbyCinemaAndDate(name, date), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No hay funciones en la fecha especificada", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{name}/{date}/{moviename}", method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionByFunctionNameandDate(@PathVariable String name, @PathVariable String date, @PathVariable String moviename) {
        try {
            return new ResponseEntity<>(cinemaServices.getFunctionByName(name, date, moviename), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No hay funciones con en ese nombre", HttpStatus.NOT_FOUND);
        }
    }

    //Métodos POST
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity<?> addNewFunction(@PathVariable String name, @RequestBody CinemaFunction function) {
        if (function.getMovie() == null || function.getDate() == null) {
            return new ResponseEntity<>("Bad Request - Invalid Parameters", HttpStatus.BAD_REQUEST);
        }
        try {
            cinemaServices.addNewFunctionByCinema(name, function);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine especificado")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else if (ex.getMessage().equals("La función que intenta añadir ya existe")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }
    
    //Métodos PUT
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFunctionByName(@PathVariable String name, @RequestBody CinemaFunction function) {
        if (function.getMovie() == null || function.getDate() == null) {
            return new ResponseEntity<>("Bad Request - Invalid Parameters", HttpStatus.BAD_REQUEST);
        }
        try {            
            cinemaServices.addNewFunctionByCinema(name, function);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine especificado")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);            
            } else {
                System.out.println("error aqui");
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);                
            }
        }
    }

    //Métodos DELETE
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFunctionByCinemaAndFunction(@PathVariable String name, @RequestBody CinemaFunction function) {
        System.out.println("Entró al método");
        try {
            System.out.println("Entró al método1");
            cinemaServices.deleteFunctionByCinemaAndFunction(cinemaServices.getCinemaByName(name), function);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine especificado")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                System.out.println("error aqui");
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
