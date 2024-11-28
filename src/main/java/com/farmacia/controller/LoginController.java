package com.farmacia.controller;

import com.farmacia.model.Usuario;
import com.farmacia.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Usuario> autenticarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = loginService.autenticarUsuario(usuario);
        if (usuarioExistente != null) {

            System.out.println("Usuário encontrado: " + usuarioExistente);
            return ResponseEntity.ok(usuarioExistente); // Retorna o objeto completo do usuário
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

