/**
import com.google.gson.Gson;
import edu.eci.arsw.cinema.CinemaAPIApplication;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CinemaAPIApplication.class})
@AutoConfigureMockMvc
public class CinemaControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private CinemaServices services;

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllCinemas() throws Exception {
        mock.perform(
                MockMvcRequestBuilders.get("/cinema")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldGetCinemaByName() throws Exception {
        mock.perform(
                MockMvcRequestBuilders.get("/cinema/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotGetNonExistentCinemaByName() throws Exception {
        mock.perform(
                MockMvcRequestBuilders.get("/cinema/cineRandom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetNonExistentFunction() throws Exception {
        mock.perform(
                MockMvcRequestBuilders.get("/cinema/cinemaX/2020-06-09 15:30/funcionRandom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetFunctionByName() throws Exception {
        mock.perform(
                MockMvcRequestBuilders.get("/cinema/cinemaX/2018-12-18 15:30/SuperHeroes Movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldAddNewFunction() throws Exception {
        Movie movie = new Movie("Shrek 3", "Comedy");
        CinemaFunction function = new CinemaFunction(movie, "2018-12-18 15:30 15:30");
        mock.perform(
                MockMvcRequestBuilders.post("/cinema/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(function))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void shouldNotUpdateNonExistentFunction() throws Exception {
        Movie movie = new Movie("RandomPelicula", "F");
        CinemaFunction function = new CinemaFunction(movie, "2018-12-18 15:30 15:30");        
        mock.perform(
                MockMvcRequestBuilders.put("/cinema/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(function.getMovie()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
*/