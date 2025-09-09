package com.br.wishlist.facade;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoFacade {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscarProdutoPorId(String produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}

