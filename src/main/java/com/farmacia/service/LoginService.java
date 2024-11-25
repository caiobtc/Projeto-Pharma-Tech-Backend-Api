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
        Usuario usuarioExistente = usuarioRepository.findByEmailOrCpf(usuario.getEmail(), usuario.getCpf());
        if (usuarioExistente != null && usuarioExistente.getSenha().equals(usuario.getSenha())) {
            return true;
        }
        return false;
    }
}