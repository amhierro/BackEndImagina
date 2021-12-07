package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public Optional<User> getById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = URLBASE + "/login/{email}/{password}")
    @ApiOperation(value = "( login ) Trae un usuario por email y password", notes = "", response = User.class)
    public Optional<User> getByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
        return userService.findByEmailAndPassword(email, password);

    }

    @GetMapping(value = URLBASE + "/user/{email}/{username}")
    @ApiOperation(value = "( login ) Trae un usuario por email y username", notes = "", response = User.class)
    public Optional<User> getByEmailAndUsername(@PathVariable("email") String email, @PathVariable("username") String username) {
        return userService.findByEmailAndUsername(email, username);

    }

    @GetMapping(value = URLBASE + "/user/username/{username}")
    @ApiOperation(value = "( findByUsername ) Trae un user por username", notes = "", response = User.class)
    public Optional<User> getByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping(value = URLBASE + "/user/email/{email}")
    @ApiOperation(value = "( findByEmail ) Trae un user por email", notes = "", response = User.class)
    public Optional<User> getByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @PostMapping(value = URLBASE + "/user")
    @ApiOperation(value = "( Post ) crea un user", notes = "", response = User.class)
    public ResponseEntity<User> postUser(@RequestBody User user) {
        Optional<User> op = getByEmail(user.getEmail());
        if (!op.isPresent()) {
            User u = userService.create(user);
            if (u == null) {
                return ResponseEntity.notFound().build();
            }else{
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("id")
                        .buildAndExpand(u.getId())
                        .toUri();
                return ResponseEntity.created(uri).body(u);
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping(value = URLBASE + "/user/{id}")
    @ResponseBody
    @ApiOperation(value = "( Put ) Modifica un user", notes = "", response = User.class)
    public ResponseEntity<User> putUser(@PathVariable("id") String id, @RequestBody User user) {
        User u = userService.update(id, user);
        if (u == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(u.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(u);
        }
    }

    @DeleteMapping(value = URLBASE + "/user")
    @ApiOperation(value = "( Delete ) Elimina a todos los users", notes = "", response = User.class)
    public String deleteAll() {
        userService.deleteAll();
        return "Se han borrado todos los users de la bbdd";
    }

    @DeleteMapping(value = URLBASE + "/user/{id}")
    @ApiOperation(value = "( Delete ) Elimina un usuario por Id", notes = "", response = User.class)
    public String deleteById(@PathVariable("id") String id) {
        userService.deleteById(id);
        return String.format("El user con Id %s ha sido eliminado", id);
    }

}


//    @GetMapping(value = URLBASE + "/user/{id}")
//    @ApiOperation(value = "( findById ) Trae un user por Id", notes = "", response = User.class)
//    public String getById(@PathVariable("id") String id) {
//        Optional<User> op = userService.getUserById(id);
//        User u;
//        if (op.isPresent()) {
//            u = op.get();
//            return u.toString();
//        } else {
//            return String.format("El usuario con el id %s no existe", id);
//        }
//    }


//    @PostMapping(value = URLBASE + "/user")
//    @ApiOperation(value = "( Post ) crea un user", notes = "", response = User.class)
//    public ResponseEntity<User> postUser(@RequestBody User user) {
//        User u = userService.create(user);
//        if (u == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                    .path("id")
//                    .buildAndExpand(u.getId())
//                    .toUri();
//            return ResponseEntity.created(uri).body(u);
//        }
//    }
