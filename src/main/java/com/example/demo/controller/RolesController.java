package com.example.demo.controller;

import com.example.demo.model.enums.Rol;
import com.example.demo.service.RolesService;
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
@Api(tags = "05-Controlador Roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping(value = URLBASE + "/roles")
    @ApiOperation(value = "( findAll ) Trae todos los roles", notes = "", response = Rol.class)
    public List<String> getAllRoles() {
        return rolesService.getAllRoles();
    }
}
