package com.farmacia.controller;

import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.farmacia.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String emailCpf = loginData.get("emailCpf");
        String senha = loginData.get("senha");

        if (usuarioService.autenticarUsuario(emailCpf, senha)) {
            Usuario usuario = usuarioService.findByEmailOrCpf(emailCpf);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login bem-sucedido!");
            response.put("user", Map.of("id", usuario.getId(), "nome", usuario.getNome())); // Dados básicos do usuário
            return ResponseEntity.ok(response);
        }

        // Caso as credenciais sejam inválidas
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Credenciais inválidas. Verifique email/CPF e senha.");
        return ResponseEntity.status(401).body(response);

    }
}
