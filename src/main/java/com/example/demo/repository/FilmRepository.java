package com.example.demo.repository;

import com.example.demo.model.Film;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends MongoRepository<Film, String> {
    public Optional<Film> findById(String id);
}
