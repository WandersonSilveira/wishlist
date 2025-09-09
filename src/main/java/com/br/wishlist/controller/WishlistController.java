package com.br.wishlist.controller;

import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{clienteId}/produtos/{produtoId}")
    public ResponseEntity<?> adicionarProdutoNaWishlist(@PathVariable String clienteId, @PathVariable String produtoId) {
        try {
            Wishlist wishlist = wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId);
            return ResponseEntity.ok(wishlist);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao adicionar produto: " + e.getMessage());
        }
    }
}
