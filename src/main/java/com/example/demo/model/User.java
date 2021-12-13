package com.example.demo.model;

import com.example.demo.model.enums.Genre;
import com.example.demo.model.enums.Rol;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Document("Users")
public class User  implements UserDetails {

    @Id
    private String id;
    private String username;
    private String avatar;              //(url al avatar del usuario)
    private String email;
    private String password;            //debe estar siempre cifrada bajo sistema AES)
    private Rol rol;
    private List<Genre> favGenders;    //Array de String (indica el/los género/s favorito/s del usuario)
    private List<String> favFilms;     //lista de IDs de películas a las que haya dado favorito


    public User(String username, String avatar, String email, String password, List<Genre> favGenders, List<String> favFilms, Rol rol) {
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

    public List<Genre> getFavGenders() {
        return favGenders;
    }

    public void setFavGenders(List<Genre> favGenders) {
        this.favGenders = favGenders;
    }

    public List<String> getFavFilms() {
        return favFilms;
    }

    public void setFavFilms(List<String> favFilms) {
        this.favFilms = favFilms;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(rol.toString()));
        return roles;
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

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
