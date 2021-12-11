package com.example.demo.service;

import com.example.demo.model.enums.Genre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerosService {

    public List<String> getAllGeneros() {
        ArrayList<String> generos = new ArrayList<String>();
        for (Genre genero : Genre.values()) {
            generos.add(genero.toString());
        }
        return generos;
    }
}
