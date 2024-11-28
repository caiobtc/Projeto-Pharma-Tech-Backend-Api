package com.farmacia.controller;

import com.farmacia.model.Usuario;
import com.farmacia.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cadastro")
@CrossOrigin(origins = "http://localhost:3000")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @PostMapping
    public String cadastrarUsuario(@RequestBody Usuario usuario) {

        System.out.println("Usuário recebido: " + usuario);
        return cadastroService.cadastrarUsuario(usuario);
    }
}