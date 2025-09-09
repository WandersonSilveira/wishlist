package com.br.wishlist.facade;

import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WishlistFacade {
    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist buscarOuCriarWishlist(String clienteId) {
        return wishlistRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    Wishlist novaWishlist = new Wishlist();
                    novaWishlist.setClienteId(clienteId);
                    novaWishlist.setProdutos(new ArrayList<>());
                    return novaWishlist;
                });
    }
}

