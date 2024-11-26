package com.farmacia.repository;

import com.farmacia.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaIgnoreCase(String categoria);

}
