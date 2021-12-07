package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByEmailAndUsername(String email, String username) {
        return userRepository.findByEmailAndUsername(email, username);
    }

    public User create(User u) {
        return userRepository.save(new User(u.getUsername(), u.getAvatar(), u.getEmail(), u.getPassword(), u.getFavGenders(), u.getFavFilms(), u.getRol()));
    }

    public User update(String id, User u) {
        Optional<User> x = userRepository.findById(id);
        User newUser;
        if (x.isPresent()) { // si existe la actualiza
            newUser = x.get();
            newUser.setUsername(u.getUsername());
            newUser.setAvatar(u.getAvatar());
            newUser.setEmail(u.getEmail());
            newUser.setPassword(u.getPassword());
            newUser.setFavGenders(u.getFavGenders());
            newUser.setFavFilms(u.getFavFilms());
            newUser.setRol(u.getRol());
            return userRepository.save(newUser);
        } else { // si no existe la crea
            return userRepository.save(new User(u.getUsername(), u.getAvatar(), u.getEmail(), u.getPassword(), u.getFavGenders(), u.getFavFilms(), u.getRol()));
        }
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(String id) {
        Optional<User> x = userRepository.findById(id);
        User deleteUser;
        if (x.isPresent()) {
            deleteUser = x.get();
            userRepository.delete(deleteUser);
        }
    }
}
