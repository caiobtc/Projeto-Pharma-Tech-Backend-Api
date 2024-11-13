package com.farmacia.service;

import com.farmacia.model.Produto;
import com.farmacia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto produto) {
        try {
            if (produto.getId() != null) {
                // Verifica se o produto já existe no banco de dados
                Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());

                // Se o produto já existe, apenas atualiza a quantidade em estoque
                if (produtoExistente.isPresent()) {
                    Produto produtoAtual = produtoExistente.get();
                    produtoAtual.setQuantidadeEmEstoque(produtoAtual.getQuantidadeEmEstoque() + produto.getQuantidadeEmEstoque());
                    return produtoRepository.save(produtoAtual);
                }
            }
            return produtoRepository.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public Produto atualizarProduto(String id, Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public void deletarProduto(String id) {
        produtoRepository.deleteById(id);
    }
}
