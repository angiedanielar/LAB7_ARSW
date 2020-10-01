package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import java.util.List;

public interface CinemaFilter {
    
    public List<CinemaFunction> Filter(String filter, List<CinemaFunction> functions) throws CinemaException;
    
}
