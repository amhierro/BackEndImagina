package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Api(tags = "02-Controlador Users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "( findAll ) Trae todos los users", notes = "", response = User.class)
    public List<User> getAll(){
        return userService.getAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "( findById ) Trae un user por Id", notes = "", response = User.class)
    public String getById(@PathVariable("id") String id){
        Optional<User> op = userService.getUserById(id);
        User u;
        if(op.isPresent()) {
            u= op.get();
            return u.toString();
        } else{
            return String.format("El usuario con el id %s no existe", id);
        }
    }


    // TODO getByUsername

    @PostMapping(value = "/user")
    @ApiOperation(value = "( Post ) crea un user", notes = "", response = User.class)
    public ResponseEntity<User> postUser(@RequestBody User user){
        User u = userService.create(user);
        if(u==null){
            return ResponseEntity.notFound().build();
        } else{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(u.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(u);
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "( Put ) Modifica un user", notes = "", response = User.class)
    public ResponseEntity<User> putUser(@PathVariable("id")String id, @RequestBody User user){
        User u= userService.update(id, user);
        if(u==null){
            return ResponseEntity.notFound().build();
        }else{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(u.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(u);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ApiOperation(value = "( Delete ) Elimina a todos los users", notes = "", response = User.class)
    public String deleteAll(){
        userService.deleteAll();
        return "Se han borrado todos los users de la bbdd";
    }

    @DeleteMapping(value = "/user/{id}")
    @ApiOperation(value = "( Delete ) Elimina un usuario por Id", notes = "", response = User.class)
    public String deleteById(@PathVariable("id") String id){
        userService.deleteById(id);
        return String.format("El user con Id %s ha sido eliminado", id );
    }

}
