/**
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cristian
 */
/**
public class InMemoryPersistenceTest {

    @Test
    public void shouldSaveNewAndLoadTest() throws CinemaPersistenceException {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);
        ipct.saveCinema(c);

        assertNotNull("Loading a previously stored cinema returned null.", ipct.getCinemaByName(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.", ipct.getCinemaByName(c.getName()), c);
    }

    @Test
    public void shouldSaveExistingCinemaTest() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("Movies Bogotá", functions);

        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }

        CopyOnWriteArrayList<CinemaFunction> functions2 = new CopyOnWriteArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3", "Action"), functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3", "Horror"), functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2 = new Cinema("Movies Bogotá", functions2);
        try {
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        } catch (CinemaPersistenceException ex) {

        }
    }

    @Test
    public void shouldNotAddRepeatedCinema() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        Cinema c1 = new Cinema("Movies Bogotá", functions);
        Cinema c2 = new Cinema("Movies Bogotá", functions);
        try {
            ipct.saveCinema(c1);
            ipct.saveCinema(c2);
        } catch (CinemaPersistenceException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldNotAddNullCinema() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        Cinema c1 = new Cinema("Movies Bogotá", functions);
        Cinema c2 = new Cinema("", functions);
        try {
            ipct.saveCinema(c1);
            ipct.saveCinema(c2);
        } catch (CinemaPersistenceException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldNotBuyTicketsWithInvalidRowOrColumn() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);

        try {
            ipct.saveCinema(c);
            ipct.buyTicket(0, 0, "Movies Bogotá", functionDate, "SuperHeroes Movie 2");
        } catch (CinemaPersistenceException ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldNotBuyTicketsWithNullParameter() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);

        try {
            ipct.saveCinema(c);
            ipct.buyTicket(5, 5, "Movies Bogotá", functionDate, null);
        } catch (CinemaPersistenceException ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldNotBuyTicketsWithInexistentCinema() {
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);

        try {
            ipct.saveCinema(c);
            ipct.buyTicket(5, 5, "Cine Random", functionDate, "SuperHeroes Movie 2");
        } catch (CinemaPersistenceException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldGetFunctionsByCinemaAndDate(){
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            ipct.getFunctionsbyCinemaAndDate("Movies Bogotá", functionDate);
        } catch (CinemaPersistenceException ex) {
            fail("Couldn't get the functions");
        }
    }
    
    @Test
    public void shouldGetAllCinemas(){
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            ipct.getCinemas();
        } catch (CinemaPersistenceException ex) {
            fail("Couldn't get all cinemas");
        }
    }
    
    @Test
    public void shouldGetCinemasByName(){
        InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);
        
        try {
            ipct.saveCinema(c);
            ipct.getCinemaByName("Movies Bogotá");
        } catch (CinemaPersistenceException ex) {
            fail("Couldn't get specified cinema");
        }
    }
    
    
}
*/