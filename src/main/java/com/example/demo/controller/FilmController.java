package com.example.demo.controller;

import com.example.demo.model.Film;
import com.example.demo.model.NewFavFilm;
import com.example.demo.model.User;
import com.example.demo.service.FilmService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.Constantes.URLBASE;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Api(tags = "01-Controlador Film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;


    @GetMapping(value = URLBASE + "/films")
    @ApiOperation(value = "( findAll ) Trae todos los films", notes = "", response = Film.class)
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping(value = URLBASE + "/film/{id}")
    @ApiOperation(value = "( findById ) Trae un film por Id", notes = "", response = Film.class)
    public String getById(@PathVariable("id") String id) {
        Optional<Film> optionalFilm = filmService.getFilmById(id);
        if (optionalFilm.isPresent()){
         return optionalFilm.get().toString();
        } else{
            return String.format("La película con id %s no existe", id);
        }
    }


    @GetMapping(value = URLBASE + "/film/fav/{id}")
    @ApiOperation(value = "( findFavUserId ) Trae los films favoritos de un usuario", notes = "", response = Film.class)
    public List<Film>getFavFilmUser(@PathVariable("id") String id) {
        Optional<User> op = userService.getUserById(id);
        User u;
        if (op.isPresent()) {
            u = op.get();
            List<Film> favoritas = new ArrayList<Film>();
            for(String idFilm:u.getFavFilms()){
                Optional<Film> fe = filmService.getFilmById(idFilm);
                Film f;
                if(fe.isPresent()) {
                    f = fe.get();
                    favoritas.add(f);
                }
            }
            return favoritas;
        } else {
            return null;
        }
    }

    @PostMapping(value = URLBASE + "/fav/film")
    @ApiOperation(value = "( Post ) Agrega un film a la favFilm de un usuario", notes = "", response = NewFavFilm.class)
    public ResponseEntity<User> postFavFilm(@RequestBody NewFavFilm newFavFilm){ // email - username - idFilm
        Optional<User> x = userService.findByEmailAndUsername(newFavFilm.getEmail(), newFavFilm.getUsername());
        User usuario;
        if (x.isPresent()) {
            usuario = x.get();
            if (!usuario.getFavFilms().contains(newFavFilm.getIdFilm())){
                usuario.getFavFilms().add(newFavFilm.getIdFilm());
                User u = userService.update(usuario.getId(), usuario);
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
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(value = URLBASE + "/film")
    @ApiOperation(value = "( Post ) Crea un film", notes = "", response = Film.class)
    public ResponseEntity<Film> postFilm(@RequestBody Film film) {
        Film f = filmService.create(film);
        if (f == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(f.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(f);
        }
    }


    @PutMapping(value = URLBASE + "/film/{id}")
    @ApiOperation(value = "( Put ) Modifica un film", notes = "", response = Film.class)
    @ResponseBody
    public ResponseEntity<Film> putFilm(@PathVariable("id") String id, @RequestBody Film film) {
        Film f = filmService.update(id, film);
        if (f == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(f.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(f);
        }
    }


    @DeleteMapping(value = URLBASE + "/film")
    @ApiOperation(value = "( Delete ) Elimina todos los films", notes = "", response = Film.class)
    public String deleteAll() {
        filmService.deleteAll();
        return "Se han borrado todos los films de la bbdd";
    }

    @DeleteMapping(value = URLBASE + "/film/{id}")
    @ApiOperation(value = "( Delete ) Elimina un film", notes = "", response = Film.class)
    public String deleteById(@PathVariable("id") String id) {
        filmService.deleteById(id);
        return String.format("El film con Id %s ha sido eliminado", id);
    }

}





//    @GetMapping(value = URLBASE + "/film/{id}")
//    @ApiOperation(value = "( findById ) Trae un film por Id", notes = "", response = Film.class)
//    public String getById(@PathVariable("id") String id) {
//        Optional<Film> op = filmService.getFilmById(id);
//        Film f;
//        if (op.isPresent()) {
//            f = op.get();
//            return f.toString();
//        } else {
//            return String.format("El film con el id %s no existe", id);
//        }
//    }
