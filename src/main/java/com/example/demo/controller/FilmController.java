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
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Optional<Film> optionalFilm = filmService.getFilmById(id);

        if (optionalFilm.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalFilm.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún film con el id: %s", id));
        }
    }

    @GetMapping(value = URLBASE + "/film/fav/{id}")
    @ApiOperation(value = "( findFavUserId ) Trae los films favoritos de un usuario", notes = "", response = Film.class)
    public ResponseEntity<?> getFavFilmUser(@PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User usuario = optionalUser.get();
            List<Film> favoritas = new ArrayList<Film>();
            if (usuario.getFavFilms() != null) {
                for (String idFilm : usuario.getFavFilms()) {
                    Optional<Film> fe = filmService.getFilmById(idFilm);
                    if (fe.isPresent()) {
                        favoritas.add(fe.get());
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(favoritas);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(favoritas);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
        }
    }

    @PostMapping(value = URLBASE + "/fav/film")
    @ApiOperation(value = "( Post ) Agrega un film a la favFilm de un usuario", notes = "", response = NewFavFilm.class)
    public ResponseEntity<?> postFavFilm(@RequestBody NewFavFilm newFavFilm) { // email - username - idFilm
        Optional<User> x = userService.findByEmailAndUsername(newFavFilm.getEmail(), newFavFilm.getUsername());
        if (x.isPresent()) {
            User usuario = x.get();
            if (usuario.getFavFilms() == null) {
                List<String> favoritas = new ArrayList<>();
                favoritas.add(newFavFilm.getIdFilm());
                usuario.setFavFilms(favoritas);
            } else if(!usuario.getFavFilms().contains(newFavFilm.getIdFilm())){
                usuario.getFavFilms().add(newFavFilm.getIdFilm());
            }
            User u = userService.update(usuario.getId(), usuario);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ha ocurrido un error al actualizar");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
    }

    @PostMapping(value = URLBASE + "/film")
    @ApiOperation(value = "( Post ) Crea un film", notes = "", response = Film.class)
    public ResponseEntity<?> postFilm(@RequestBody Film film) {
        Film f = filmService.create(film);
        if (f == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ha ocurrido un error al crear el film");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(f);
        }
    }

    @PutMapping(value = URLBASE + "/film/{id}")
    @ApiOperation(value = "( Put ) Modifica un film", notes = "", response = Film.class)
    @ResponseBody
    public ResponseEntity<?> putFilm(@PathVariable("id") String id, @RequestBody Film film) {
        Optional<Film> optionalfilm = this.filmService.getFilmById(id);
        if (optionalfilm.isPresent()) {
            Film f = filmService.update(id, film);
            if (f == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ha ocurrido un error al actualizar el film");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(f);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El film no existe");
        }
    }

    @DeleteMapping(value = URLBASE + "/film/{id}")
    @ApiOperation(value = "( Delete ) Elimina un film", notes = "", response = Film.class)
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        boolean isRemoved = filmService.deleteById(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("El film con id: %s no existe", id));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(String.format("El film con id: %s ha sido eliminado", id));
        }
    }

    @DeleteMapping(value = URLBASE + "/film")
    @ApiOperation(value = "( Delete ) Elimina todos los films", notes = "", response = Film.class)
    public ResponseEntity<String> deleteAll() {
        this.filmService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Se han borrado todos los films de la bbdd");
    }

}
