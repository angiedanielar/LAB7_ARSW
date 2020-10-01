/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import java.util.List;
import java.util.Set;

/**
 *
 * @author cristian
 */
public interface CinemaPersitence {
    
    /**
     * 
     * @param row the row of the seat
     * @param col the column of the seat
     * @param cinema the cinema's name
     * @param date the date of the function
     * @param movieName the name of the movie     * 
     * @throws CinemaPersistenceException if the seat is occupied,
     * or any other low-level persistence error occurs.
     */
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException;
    
    /**
     * 
     * @param cinema cinema's name
     * @param date date
     * @return the list of the functions of the cinema in the given date
     */
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException;
    
    /**
     * 
     * @param cinema new cinema
     * @throws  CinemaPersistenceException n if a cinema with the same name already exists
     */
    public void saveCinema(Cinema cinema) throws CinemaPersistenceException;
    
    /**     
     * @param name name of the cinema
     * @return Cinema of the given name
     * @throws  CinemaPersistenceException if there is no such cinema
     */
    public Cinema getCinemaByName(String name) throws CinemaPersistenceException;
    
    /**
     * 
     *  obtiene todos los cinemas
     * @return 
     * @throws edu.eci.arsw.cinema.persistence.CinemaPersistenceException
     */
    public Set<Cinema> getCinemas() throws CinemaPersistenceException;

    public List<CinemaFunction> getFunctionByName(String cinema, String date, String moviename) throws CinemaPersistenceException;
    
    public void addFunctionByName(String cinema, CinemaFunction function) throws CinemaPersistenceException;
    
    public void updateFunctionByName(String cinema, CinemaFunction function) throws CinemaPersistenceException;

    public CinemaFunction getFunctionbyCinemaAndDateAndMovie(String name, String date, String moviename) throws CinemaPersistenceException;

    void deleteFunctionByCinemaAndFunction(Cinema name, CinemaFunction function) throws CinemaPersistenceException;
}
