/**
import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.*;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.filter.*;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CinemaFilterTest {  
    
    //Prueba para @Qualifier("DisponibleFiltro") en CinemaServices
    /**
    @Test
    public void shouldFilterByAvailabilty() throws CinemaException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cinemaServices = applicationContext.getBean(CinemaServices.class);

        Movie m = new Movie("Inception", "Suspense");
        Movie m2 = new Movie("Coraline", "Horror");
        CopyOnWriteArrayList<CinemaFunction> f = new CopyOnWriteArrayList<>();
        CinemaFunction cf = new CinemaFunction(m, "2018-07-15 12:00");
        CinemaFunction cf2 = new CinemaFunction(m2, "2018-07-15 12:00");
        f.add(cf);
        f.add(cf2);
        Cinema cinema = new Cinema("CineARSW", f);
        cinemaServices.addNewCinema(cinema);
        for (int i = 1; i < 12; i++) {
            cinemaServices.buyTicket(1, i, "CineARSW", "2018-07-15 12:00", "Coraline");
        }
        List<CinemaFunction> result;
        String expected = "[CinemaFunction{movie=Movie{name=Inception, genre=Suspense}, date=2018-07-15 12:00}]";        
        result = cinemaServices.getFunctionsbyFilter("CineARSW", "2018-07-15 12:00", "80");

        assertEquals(result.toString(), expected);
    }*/

    //Prueba para @Qualifier("DisponibleFiltro") en CinemaServices
/**
    @Test
    public void shouldFilterByGenre() throws CinemaException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cinemaServices = applicationContext.getBean(CinemaServices.class);

        Movie m = new Movie("Inception", "Suspense");
        Movie m2 = new Movie("Coraline", "Horror");
        CopyOnWriteArrayList<CinemaFunction> f = new CopyOnWriteArrayList<>();
        CinemaFunction cf = new CinemaFunction(m, "2018-07-15 12:00");
        CinemaFunction cf2 = new CinemaFunction(m2, "2018-07-15 12:00");
        f.add(cf);
        f.add(cf2);
        Cinema cinema = new Cinema("CineARSW", f);
        cinemaServices.addNewCinema(cinema);
        
        List<CinemaFunction> result;
        String expected = "[CinemaFunction{movie=Movie{name=Inception, genre=Suspense}, date=2018-07-15 12:00}]";        
        result = cinemaServices.getFunctionsbyFilter("CineARSW", "2018-07-15 12:00", "Suspense");

        assertEquals(result.toString(), expected);
    }

}
* */
