package com.br.wishlist.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
@Getter
@Setter
public class Cliente {
    @Id
    private String id;
    private String nome;
    private String email;
}

