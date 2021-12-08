package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("Films")
public class Film {

    @Id
    private String id;
    private String titulo;
    private String director;
    private int anio;
    private String sinopsis;
    private String poster;
    private List<String> genero;


    public Film(String titulo, List<String> genero, int anio, String director, String sinopsis, String poster) {
        super();
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.director = director;
        this.sinopsis = sinopsis;
        this.poster = poster;
    }

    public Film() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getGenero() {
        return genero;
    }

    public void setGenero(List<String> genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", anio=" + anio +
                ", director='" + director + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
