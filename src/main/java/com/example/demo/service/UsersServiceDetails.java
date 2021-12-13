package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.enums.Rol;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceDetails implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return this.repository.findAll(Sort.by("username").ascending());
    }

    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public List<User> findByRol(Rol rol) {
        return this.repository.findByRol(rol);
    }

    public Optional<User> create(User usuario) {
        Optional<User> x = this.repository.findByEmail(usuario.getEmail());
        User newUser;
        if (x.isPresent()) { // si existe la actualiza
            newUser = x.get();
            // usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return Optional.of(this.repository.save(newUser));
        } else {
            return Optional.empty();
        }

    }

    public Optional<User> findById(String id) {
        return this.repository.findById(id);
    }

    public List<String> findFilmFavByUserID(String userID) {
        Optional<User> optionalUser = this.repository.findById(userID);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getFavFilms();
        }
        return new ArrayList<>();
    }

    public Boolean update(String id, User user) {
        asignarValoresPorDefecto(user);
        Optional<User> optional = this.repository.findById(id);
        if (optional.isPresent()) {
            user.setId(id);
            this.repository.save(user);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void asignarValoresPorDefecto(User user) {
        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            user.setAvatar("https://store.playstation.com/store/api/chihiro/00_09_000/container/US/en/19/UP1415-CUSA03724_00-AV00000000000163/image?w=320&h=320&bg_color=000000&opacity=100&_version=00_09_000");
        }
        if (user.getFavGenders() == null || user.getFavGenders().isEmpty()) {
            user.setFavGenders(new ArrayList<>());
        }
        if (user.getFavFilms() == null || user.getFavFilms().isEmpty()) {
            user.setFavFilms(new ArrayList<>());
        }

    }

    public Boolean deleteById(String id) {
        Optional<User> optional = this.repository.findById(id);
        if (optional.isPresent()) {
            this.repository.delete(optional.get());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> x = this.repository.findByUsername(username);
        if (x.isPresent()) { // si existe la actualiza
            User userMB = x.get();
            if (userMB.getUsername().equals(username)) {
                org.springframework.security.core.userdetails.User user =
                        new org.springframework.security.core.userdetails.User(userMB.getUsername(),
                                passwordEncoder.encode(userMB.getPassword()), userMB.getAuthorities());
                return user;
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }


}
