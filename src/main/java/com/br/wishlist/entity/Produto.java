package com.br.wishlist.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produtos")
@Getter
@Setter
public class Produto {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private Double preco;
}

