package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.demo.utils.Constantes.URLBASE;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        Optional<User> user = userService.findByEmail(authenticationRequest.getEmail());
        if (user.isPresent()) {
            try {

                authenticate(user.get().getUsername(), user.get().getPassword());
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado por email o password");
            }
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(user.get().getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Acceso denegado por email o password");

    }

    @GetMapping(value = URLBASE + "/login/{email}/{password}")
    @ApiOperation(value = "( login ) Trae un usuario por email y password", notes = "", response = User.class)
    public ResponseEntity<?> getByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
        Optional<User> usuario = this.userService.findByEmailAndPassword(email, password);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No existe ningún usuario con el email: %s y la password aportada", email));
        }
    }

    private void authenticate(String username, String password) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        }
    }

}
