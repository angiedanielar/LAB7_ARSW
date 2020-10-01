package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service("GeneroFiltro")
public class GenreFilter implements CinemaFilter {

    public GenreFilter() {

    }
    
    @Override
    public List<CinemaFunction> Filter(String genre, List<CinemaFunction> functions) throws CinemaException {
        if (functions == null || genre == null) {
            throw new CinemaException("No pueden haber valores vacíos para aplicar filtro");
        }
        if (functions.isEmpty()) {
            throw new CinemaException("No hay funciones disponibles");
        }
        List<CinemaFunction> filteredFunctions = new ArrayList<>();
        for (CinemaFunction func : functions) {
            Movie m = func.getMovie();
            if (m.getGenre().equals(genre)) {
                filteredFunctions.add(func);
            }
        }        
        return filteredFunctions;
    }

}
