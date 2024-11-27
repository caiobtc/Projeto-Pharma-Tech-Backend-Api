package com.farmacia.dto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private String id;
    private String nome;
    private String descricao;
    private double preco;
    private String marca;
    private int quantidadeEmEstoque;
    private String categoria;
    private String imagemUrl;
}
