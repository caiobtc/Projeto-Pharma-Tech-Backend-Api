package com.farmacia.service;

import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean autenticarUsuario(String emailCpf, String senha) {
        Usuario usuario = findByEmailOrCpf(emailCpf);
        // Verificar se o usuário existe e se a senha está correta
        return usuario != null && passwordEncoder.matches(senha, usuario.getSenha());
    }

    public Usuario findByEmailOrCpf(String emailCpf) {
        return usuarioRepository.findByEmailOrCpf(emailCpf);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        // Criptografar senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
}
