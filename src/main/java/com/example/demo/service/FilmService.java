package com.example.demo.service;


import com.example.demo.model.Film;
import com.example.demo.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAll(){
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(String id){
        return filmRepository.findById(id);
    }

    public Film create(Film f){
        return filmRepository.save(new Film(f.getTitulo(), f.getGenero(), f.getAnio(), f.getDirector(), f.getSinopsis(), f.getPoster()));
    }

    public Film update(String id, Film f) {
        Optional<Film> x = filmRepository.findById(id);
        Film newFilm;
        if(x.isPresent()){ // si existe la actualiza
            newFilm = x.get();
            newFilm.setTitulo(f.getTitulo());
            newFilm.setGenero(f.getGenero());
            newFilm.setAnio(f.getAnio());
            newFilm.setDirector(f.getDirector());
            newFilm.setSinopsis(f.getSinopsis());
            newFilm.setPoster(f.getPoster());
            return filmRepository.save(newFilm);
        } else{ // si no existe la crea
            return filmRepository.save(new Film(f.getTitulo(), f.getGenero(), f.getAnio(),f.getDirector(), f.getSinopsis(), f.getPoster()));
        }
    }

    public void deleteAll(){
        filmRepository.deleteAll();
    }

    public void deleteById(String id){
        Optional<Film> x = filmRepository.findById(id);
        Film deleteFilm;
        if(x.isPresent()){
            deleteFilm = x.get();
            filmRepository.delete(deleteFilm);
        }
    }

}
