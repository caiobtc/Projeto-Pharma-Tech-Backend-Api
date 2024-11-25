package com.farmacia.repository;

import com.farmacia.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
}
