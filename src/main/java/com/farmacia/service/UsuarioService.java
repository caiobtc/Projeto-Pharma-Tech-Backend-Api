// src/service/UsuarioService.java
package com.farmacia.service;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario atualizarUsuario(String id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
        usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
        // Atualize outros campos conforme necessário

        return usuarioRepository.save(usuarioExistente);
    }
}
