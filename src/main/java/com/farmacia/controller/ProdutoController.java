package com.farmacia.controller;

import com.farmacia.dto.ProdutoDTO;
import com.farmacia.model.Produto;
import com.farmacia.repository.ProdutoRepository;
import com.farmacia.service.ProdutoService;
import com.farmacia.service.VendaRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/vender")
    public ResponseEntity<String> venderProduto(@RequestBody VendaRequest vendaRequest) {
        Optional<Produto> produtoOptional = produtoRepository.findById(vendaRequest.getIdProduto());
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            if (produto.getQuantidadeEmEstoque() >= vendaRequest.getQuantidade()) {
                produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - vendaRequest.getQuantidade());
                produtoService.criarProduto(produto); // Atualiza o produto no banco
                return ResponseEntity.ok("Venda realizada com sucesso");
            } else {
                return ResponseEntity.badRequest().body("Estoque insuficiente para a venda.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Produto> listarTodosProdutos() {
        List<Produto> produtos = produtoService.listarTodosProdutos();

        produtos.forEach(produto -> {
            if (produto.getImagemProduto() != null && produto.getImagemProduto().length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(produto.getImagemProduto());
                produto.setImagemUrl("data:image/jpeg;base64," + base64Image);
            }
        });
        return produtos;
    }



    @PostMapping("/cadastrar-produto")
    public ResponseEntity<Produto> criarProduto(
            @RequestParam("produto") String produtoJson,
            @RequestParam("imagem") MultipartFile imagemFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Produto produto = objectMapper.readValue(produtoJson, Produto.class);

            // Converte a imagem para um array de bytes e armazena no banco de dados
            produto.setImagemProduto(imagemFile.getBytes());

            // Salva o produto no banco de dados
            Produto novoProduto = produtoService.criarProduto(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable String id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable String id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok("Produto com ID " + id + " foi excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto com ID " + id + " não encontrado.");
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarProdutosNome(@RequestParam("nome") String nome) {
        List<Produto> produtosEncontrados = produtoRepository.findByNomeContainingIgnoreCase(nome);
        produtosEncontrados.forEach(produto -> {
            if (produto.getImagemProduto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(produto.getImagemProduto());
                produto.setImagemUrl("data:image/png;base64," + base64Image);
            }
        });
//        if(produtosEncontrados.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
            return ResponseEntity.ok(produtosEncontrados);
       }

    @GetMapping("/categoria")
    public List<Produto> buscarPorCategoria(@RequestParam String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);

        produtos.forEach(produto -> {
            if (produto.getImagemProduto() != null && produto.getImagemProduto().length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(produto.getImagemProduto());
                produto.setImagemUrl("data:image/jpeg;base64," + base64Image);
            }
        });

        return produtos;
    }
}
