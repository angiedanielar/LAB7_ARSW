package edu.eci.arsw.cinema;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.services.CinemaServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class CinemaMain {

    public static void main(String[] args) throws CinemaException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cinemaServices = applicationContext.getBean(CinemaServices.class);
        //Register a new cinema
        Cinema cinema = registerCinema("CineARSW"); 
        cinemaServices.addNewCinema(cinema);
        System.out.println("-----WELCOME TO THE CINEMA SYSTEM-----");
        //Consulting cinemas
        System.out.println("Consulting all cinemas");
        System.out.println(cinemaServices.getAllCinemas());                      
        //Buying tickets for Inception
        for (int i = 2; i < 12; i++) {
            cinemaServices.buyTicket(1, i, "CineARSW", "2018-07-15 12:00", "Inception");
        }
        //Buying tickets for Coraline
        for (int i = 2; i < 3; i++) {
            cinemaServices.buyTicket(1, i, "CineARSW", "2018-07-15 12:00", "Coraline");
        }
        //Gerne Filter
        System.out.println("Filtering by genre");
        System.out.println(cinemaServices.getFunctionsbyFilter("CineARSW", "2018-07-15 12:00", "Suspense"));
        /**Availabilty Filter
        System.out.println("Filtering by seats");
        System.out.println(cinemaServices.getFunctionsbyFilter("CineARSW", "2018-07-15 12:00", "80")); */      
        //Consulting a cinema
        System.out.println("Consulting cinemaX");
        System.out.println(cinemaServices.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:30"));
    }

    private static Cinema registerCinema(String name) {
        Movie m = new Movie("Inception", "Suspense"); 
        Movie m2 = new Movie("Coraline", "Horror");
        CopyOnWriteArrayList<CinemaFunction> f = new CopyOnWriteArrayList<>();        
        CinemaFunction cf = new CinemaFunction(m, "2018-07-15 12:00");
        CinemaFunction cf2 = new CinemaFunction(m2, "2018-07-15 12:00");
        f.add(cf);
        f.add(cf2);
        return new Cinema(name, f);
    }
}