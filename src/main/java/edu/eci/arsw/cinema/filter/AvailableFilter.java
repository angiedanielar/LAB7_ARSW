package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service("DisponibleFiltro")
public class AvailableFilter implements CinemaFilter {

    public AvailableFilter() {

    }
    
    @Override
    public List<CinemaFunction> Filter(String seatsNumber, List<CinemaFunction> functions) throws CinemaException {
        if (functions == null) {
            throw new CinemaException("No pueden haber valores vacíos para aplicar filtro");
        }
        int seats = Integer.parseInt(seatsNumber);
        if (seats <= 0) {
            throw new CinemaException("Debe escoger al menos 1 asiento para filtrar. La sala tiene maximo 84 asientos");
        }        
        if (functions.isEmpty()) {
            throw new CinemaException("No hay funciones disponibles");
        }       
        List<CinemaFunction> filteredFunctions = new ArrayList<>();
        for (CinemaFunction func : functions) {
            if (func.getEmptySeats() > seats) {
                filteredFunctions.add(func);
            }
        }        
        return filteredFunctions;
    }
}
