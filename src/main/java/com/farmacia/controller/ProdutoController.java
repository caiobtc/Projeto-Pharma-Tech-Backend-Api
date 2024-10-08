package com.farmacia.controller;

import com.farmacia.model.Produto;
import com.farmacia.repository.ProdutoRepository;
import com.farmacia.service.ProdutoService;
import com.farmacia.service.VendaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return produtoService.listarTodosProdutos();
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable String id, @RequestBody Produto produto) {
       Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
       return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable String id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}






















