package com.br.wishlist.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlists")
@Getter
@Setter
public class Wishlist {
    @Id
    private String id;
    private String clienteId;
    private List<Produto> produtos;
}
