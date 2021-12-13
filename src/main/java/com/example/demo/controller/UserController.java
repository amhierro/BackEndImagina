package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.Constantes.URLBASE;


@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Api(tags = "02-Controlador Users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = URLBASE + "/users")
    @ApiOperation(value = "( findAll ) Trae todos los users", notes = "", response = User.class)
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = URLBASE + "/user/{id}")
    @ApiOperation(value = "( findById ) Trae un user por Id", notes = "", response = User.class)
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Optional<User> usuario = this.userService.getUserById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún usuario con el id: %s", id));
        }
    }

    @GetMapping(value = URLBASE + "/user/{email}/{username}")
    @ApiOperation(value = "( login ) Trae un usuario por email y username", notes = "", response = User.class)
    public ResponseEntity<?> getByEmailAndUsername(@PathVariable("email") String email, @PathVariable("username") String username) {
        Optional<User> usuario = this.userService.findByEmailAndUsername(email, username);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún usuario con el email: %s y el username: %s", email, username));
        }
    }

    @GetMapping(value = URLBASE + "/user/username/{username}")
    @ApiOperation(value = "( findByUsername ) Trae un user por username", notes = "", response = User.class)
    public ResponseEntity<?> getByUsername(@PathVariable("username") String username) {
        Optional<User> usuario = this.userService.findByUsername(username);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún usuario con el username: %s", username));
        }
    }

    @GetMapping(value = URLBASE + "/user/email/{email}")
    @ApiOperation(value = "( findByEmail ) Trae un user por email", notes = "", response = User.class)
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        Optional<User> usuario = this.userService.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún usuario con el email: %s", email));
        }
    }

    @PostMapping(value = URLBASE + "/user")
    @ApiOperation(value = "( Post ) crea un user", notes = "", response = User.class)
    public ResponseEntity<?> postUser(@RequestBody User user) {

        Optional<User> usuario = this.userService.findByEmail(user.getEmail());

        if (usuario.isEmpty()) {
            User u = this.userService.create(user);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ha ocurrido un error al crear el usuario");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("El usuario con el email: %s ya existe", usuario.get().getEmail()));
        }
    }

    @PutMapping(value = URLBASE + "/user/{id}")
    @ApiOperation(value = "( Put ) Modifica un user", notes = "", response = User.class)
    public ResponseEntity<?> putUser(@PathVariable("id") String id, @RequestBody User user) {
        Optional<User> usuario = this.userService.getUserById(id);
        if (usuario.isPresent()) {
            User u = userService.update(id, user);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ha ocurrido un error al actualizar el usuario");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
        }
    }

    @DeleteMapping(value = URLBASE + "/user/{id}")
    @ApiOperation(value = "( Delete ) Elimina un usuario por Id", notes = "", response = User.class)
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        boolean isRemoved = userService.deleteById(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("El usuario con id: %s no existe", id));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(String.format("El usuario con id: %s ha sido eliminado", id));
        }
    }

    @DeleteMapping(value = URLBASE + "/user")
    @ApiOperation(value = "( DeleteAll ) Elimina a todos los users", notes = "")
    public ResponseEntity<String> deleteAll() {
        this.userService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Se han borrado todos los users de la bbdd");
    }

}

