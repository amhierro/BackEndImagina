package com.example.demo.model;

import com.example.demo.model.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@Document("Users")
public class User {

    @Id
    private String id;
    private String username;
    private String avatar;              //(url al avatar del usuario)
    private String email;
    private String password;            //debe estar siempre cifrada bajo sistema AES)
    private List<String> favGenders;    //Array de String (indica el/los género/s favorito/s del usuario)
    private List<String> favFilms;     //lista de IDs de películas a las que haya dado favorito
    private Role rol;

    public User(String username, String avatar, String email, String password, List<String> favGenders, List<String> favFilms, Role rol) {
        super();
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.favGenders = favGenders;
        this.favFilms = favFilms;
        this.rol = rol;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFavGenders() {
        return favGenders;
    }

    public void setFavGenders(List<String> favGenders) {
        this.favGenders = favGenders;
    }

    public List<String> getFavFilms() {
        return favFilms;
    }

    public void setFavFilms(List<String> favFilms) {
        this.favFilms = favFilms;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", favGenders=" + favGenders +
                ", favFilms=" + favFilms +
                ", rol=" + rol +
                '}';
    }
}
