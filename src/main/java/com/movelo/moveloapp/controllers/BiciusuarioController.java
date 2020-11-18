package com.movelo.moveloapp.controllers;

import com.movelo.moveloapp.controllers.utils.ActualizarKmWrapper;
import com.movelo.moveloapp.gestores.GestorProxy;
import com.movelo.moveloapp.models.Arbol;
import com.movelo.moveloapp.models.Biciusuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bikeriders")
public class BiciusuarioController {

    @Autowired
    private GestorProxy proxy;

    @PostMapping
    public ResponseEntity<?> createRider(@RequestBody Biciusuario rider) {
        boolean checked = proxy.registerBiciusuario(rider);
        HttpStatus status = HttpStatus.CREATED;

        if (!checked) {
            status = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(status).body(checked);
    }

    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam(value = "email") String email,
            @RequestParam(value = "pass") String pass) {
        Biciusuario userLogged = proxy.login(email, pass);

        if (userLogged == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userLogged);
    }
    @PostMapping("/kmCovered")
    public ResponseEntity<?> actualizarKm(@RequestBody ActualizarKmWrapper body) {
        boolean checked = proxy.actualizarDistancia(body.getEmail(), body.getDistance());
        HttpStatus status = HttpStatus.CREATED;
        if (!checked) {
            status = HttpStatus.NOT_MODIFIED;
        }
        return ResponseEntity.status(status).body(checked);
    }

}
