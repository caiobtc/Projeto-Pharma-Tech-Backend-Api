package com.farmacia.service;

import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean autenticarUsuario(Usuario usuario) {
        try {
            Usuario usuarioExistente = null;

            if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
            } else if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()) {
                usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf());
            }

            if (usuarioExistente != null && usuarioExistente.getSenha().equals(usuario.getSenha())) {
                return true;
            }
        } catch (Exception e) {
            // Registrar o erro para ajudar na depuração
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }

        return false;
    }
}

