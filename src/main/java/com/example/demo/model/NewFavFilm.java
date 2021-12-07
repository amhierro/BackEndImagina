package com.example.demo.model;

public class NewFavFilm {
    private String email;
    private String username;
    private String idFilm;

    public NewFavFilm() {
    }

    public NewFavFilm(String email, String username, String idFilm) {
        this.email = email;
        this.username = username;
        this.idFilm = idFilm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }
}
