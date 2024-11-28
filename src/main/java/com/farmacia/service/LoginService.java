package com.farmacia.service;

import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticarUsuario(Usuario usuario) {
        try {
            Usuario usuarioExistente = null;

            // Verifique se é um login por email ou CPF
            if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail()).orElse(null);
            } else if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()) {
                usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf()).orElse(null);
            }

            // Verifique se o usuário foi encontrado e se a senha está correta
            if (usuarioExistente != null && usuarioExistente.getSenha().equals(usuario.getSenha())) {
                return usuarioExistente; // Retorna o usuário completo, com todos os campos
            }
        } catch (Exception e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }

        return null;
    }
}



