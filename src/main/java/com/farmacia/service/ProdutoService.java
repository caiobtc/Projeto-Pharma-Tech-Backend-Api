package com.farmacia.service;

import com.farmacia.dto.ProdutoDTO;
import com.farmacia.model.Produto;
import com.farmacia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> converterParaProdutoDTO(List<Produto> produtos) {
        return produtos.stream().map(produto -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setDescricao(produto.getDescricao());
            dto.setPreco(produto.getPreco());
            dto.setMarca(produto.getMarca());
            dto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
            dto.setCategoria(produto.getCategoria());

            // Verifique e converta a imagem se estiver presente
            if (produto.getImagemProduto() != null && produto.getImagemProduto().length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(produto.getImagemProduto());
                dto.setImagemUrl("data:image/jpeg;base64," + base64Image);
            }

            return dto;
        }).toList();
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

//    public List<ProdutoDTO> buscarProdutoNome(String nome) {
//        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
//        return converterParaProdutoDTO(produtos);
//    }
    public List<Produto> buscarProdutoNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

//    public List<ProdutoDTO> buscarPorCategoria(String categoria) {
//        List<Produto> produtos = produtoRepository.findByCategoriaIgnoreCase(categoria);
//        return converterParaProdutoDTO(produtos);
//    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

}
