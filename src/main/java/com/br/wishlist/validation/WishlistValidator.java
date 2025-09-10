package com.br.wishlist.validation;

import com.br.wishlist.entity.Wishlist;

public class WishlistValidator {

    public static boolean produtoJaExisteNaWishlist(Wishlist wishlist, String produtoId) {
        return wishlist.getProdutos().stream()
                .anyMatch(p -> p.getId().equals(produtoId));
    }

    public static void validarLimiteProdutos(Wishlist wishlist) {
        if (wishlist.getProdutos().size() >= 20) {
            throw new RuntimeException("A wishlist pode ter no máximo 20 produtos.");
        }
    }
}

