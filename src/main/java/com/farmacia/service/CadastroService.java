package com.farmacia.service;

import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String cadastrarUsuario(Usuario usuario) {
        // Lógica para salvar o usuário no banco de dados
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso!";
    }
}
