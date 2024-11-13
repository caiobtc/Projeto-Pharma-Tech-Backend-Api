package com.farmacia.controller;

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
        if(produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            if(produto.getQuantidadeEmEstoque() >= vendaRequest.getQuantidade()) {
                produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - vendaRequest.getQuantidade());
                produtoRepository.save(produto);
                return ResponseEntity.ok("Venda realizada com sucesso");
            }else {
                return ResponseEntity.badRequest().body("Estoque insuficiente para a venda.");
            }
        }else {
            ResponseEntity.notFound().build();
        }
        return null;
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

    @PostMapping
    public ResponseEntity<Produto> criarProduto(
        @RequestParam("produto") String produtoJson,
        @RequestParam("imagem") MultipartFile imagemFile) {

        try {
            System.out.println("Recebendo JSON do produto: " + produtoJson);
            System.out.println("Recebendo arquivo de imagem: " + imagemFile.getOriginalFilename());

            ObjectMapper objectMapper = new ObjectMapper();
            Produto produto = objectMapper.readValue(produtoJson, Produto.class);

            // Converte a imagem para um array de bytes e armazena no banco de dados
            produto.setImagemProduto(imagemFile.getBytes());

            // Salva o produto no banco de dados
            Produto novoProduto = produtoService.criarProduto(produto);
            System.out.println("Produto salvo com sucesso: " + novoProduto);

            // Retorna o produto criado
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro inesperado: " + e.getMessage());
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
//        produtoService.deletarProduto(id);
//        return ResponseEntity.noContent().build();
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if(produtoOptional.isPresent()) {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok("Produto com ID " + id + " foi excluido com sucesso.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto com ID " + id + " não encontrado,");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable String id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}






















