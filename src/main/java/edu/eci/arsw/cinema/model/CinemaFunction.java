/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import edu.eci.arsw.cinema.persistence.CinemaException;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author cristian
 */
public class CinemaFunction {
    
    private Movie movie;
    private CopyOnWriteArrayList<List<Boolean>> seats=new CopyOnWriteArrayList<>();
    private String date;
    private int emptySeats;
    
    public CinemaFunction(){}
    
    public CinemaFunction(Movie movie, String date){
        this.movie=movie;
        this.date=date;
        for (int i=0;i<7;i++){
            CopyOnWriteArrayList<Boolean> row= new CopyOnWriteArrayList<>(Arrays.asList(new Boolean[12]));
            Collections.fill(row, Boolean.TRUE);
            this.seats.add(row);
        }
        this.emptySeats=84;
    }
    
    public void buyTicket(int row,int col) throws CinemaException{
        if (seats.get(row).get(col).equals(true)){
            seats.get(row).set(col,Boolean.FALSE);
            emptySeats-=1;
        }
        else{
            throw new CinemaException("Seat already booked");
        }
    }
    
    public List<List<Boolean>> getSeats() {
        return this.seats;
    }
    
    public int getEmptySeats() {
        return this.emptySeats;
    }
    
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CinemaFunction{" + "movie=" + movie + ", date=" + date + '}';
    }


}
