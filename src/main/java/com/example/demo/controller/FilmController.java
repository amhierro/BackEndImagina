package com.example.demo.controller;

import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.Constantes.URLBASE;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Api(tags = "01-Controlador Film")
public class FilmController {

    @Autowired
    private FilmService filmService;


    @RequestMapping(value = URLBASE + "/films", method = RequestMethod.GET)
    @ApiOperation(value = "( findAll ) Trae todos los films", notes = "", response = Film.class)
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @RequestMapping(value = URLBASE + "/film/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "( findById ) Trae un film por Id", notes = "", response = Film.class)
    public String getById(@PathVariable("id") String id) {
        Optional<Film> op = filmService.getFilmById(id);
        Film f;
        if (op.isPresent()) {
            f = op.get();
            return f.toString();
        } else {
            return String.format("El film con el id %s no existe", id);
        }
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

    @RequestMapping(value = URLBASE + "/film/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "( Put ) Modifica un film", notes = "", response = Film.class)
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

    @RequestMapping(value = URLBASE + "/film", method = RequestMethod.DELETE)
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
