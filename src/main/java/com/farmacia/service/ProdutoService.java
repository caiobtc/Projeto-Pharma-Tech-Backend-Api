package com.farmacia.service;

import com.farmacia.model.Produto;
import com.farmacia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(String id, Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public void deletarProduto(String id) {
        produtoRepository.deleteById(id);
    }
}
