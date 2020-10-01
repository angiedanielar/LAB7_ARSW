/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import edu.eci.arsw.cinema.persistence.CinemaException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author cristian
 */
public class Cinema {

    private String name;
    private CopyOnWriteArrayList<CinemaFunction> functions;

    public Cinema() {
    }

    public Cinema(String name, CopyOnWriteArrayList<CinemaFunction> functions) {
        this.name = name;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CinemaFunction> getFunctions() {
        return this.functions;
    }

    public List<CinemaFunction> getFunctionsDate(String date) throws CinemaException {
        List<CinemaFunction> functionsDate = new ArrayList<>();
        for (CinemaFunction func : functions) {
            String fecha = func.getDate().split(" ")[0];
            if (fecha.equals(date)){
                functionsDate.add(func);
            }
            else{
                throw new CinemaException("No existen funciones en esta fecha");
            }
        }

        return functionsDate;
    }

    public List<CinemaFunction> getFunctionByName(String name) throws CinemaException {
        List<CinemaFunction> function = new ArrayList<>();
        for (CinemaFunction func : functions) {
            if (func.getMovie().equals(name)) {
                function.add(func);
            }
        }
        return function;
    }

    public void setSchedule(CopyOnWriteArrayList<CinemaFunction> functions) {
        this.functions = functions;
    }

    public void addNewFunction(CinemaFunction function) throws CinemaException {
        for (CinemaFunction func : functions) {
            if (func.equals(function)) {
                throw new CinemaException("La función que intenta añadir ya existe");
            }
        }
        functions.add(function);
    }

    public void updateFunction(CinemaFunction function) throws CinemaException {
        boolean updated = false;
        for (CinemaFunction func : functions) {
            if (func.getMovie().equals(function.getMovie()) && func.getDate().equals(function.getDate())) {
                updated = true;
                break;
            }
        }       
        if (!updated) {
            functions.add(function);
        }
    }

    public CinemaFunction existeFuncion(String name, String date) throws CinemaException {
        //para que no de error se deja null
        CinemaFunction funcion = null;
        for (CinemaFunction f : functions) {
            if ((f.getMovie().getName().equals(name)) && (f.getDate().equals(date))) {
                funcion = f;
                break;
            }
        }
        return funcion;
    }

    @Override
    public String toString() {
        return "Cinema{" + "name=" + name + ", functions=" + functions + '}';
    }

    public CinemaFunction getFunctionDateAndMovie(String date, String moviename)  throws CinemaException {
        List<CinemaFunction> function = getFunctionsDate(date);

        for (CinemaFunction f: function){
            if (f.getMovie().getName().equals(moviename)){
                return f;
            }
        }
        throw new CinemaException("No exists a function with that movie name");
    }

    public void deleteFunction(CinemaFunction function) {
        int index=0;
        Iterator<CinemaFunction> iterator = functions.iterator();
        while(iterator.hasNext()){
            CinemaFunction nextfunction = iterator.next();
            if(nextfunction.getMovie().getName().equals(function.getMovie().getName())){
                index=functions.indexOf(nextfunction);
            }
        }
        functions.remove(index);
    }


}
