package com.example.demo.controller;

import com.example.demo.model.enums.Genre;
import com.example.demo.service.GenerosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.utils.Constantes.URLBASE;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Api(tags = "04-Controlador Géneros")
public class GenerosController {

    @Autowired
    private GenerosService generosService;

    @GetMapping(value = URLBASE + "/generos")
    @ApiOperation(value = "( findAll ) Trae todos los generos", notes = "", response = Genre.class)
    public List<String> getAllGeneros() {
        return generosService.getAllGeneros();
    }
}
