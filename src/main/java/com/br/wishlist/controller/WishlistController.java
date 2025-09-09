package com.br.wishlist.controller;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{clienteId}/produtos/{produtoId}")
    public ResponseEntity<?> removerProdutoDaWishlist(@PathVariable String clienteId, @PathVariable String produtoId) {
        try {
            Wishlist wishlist = wishlistService.removerProdutoDaWishlist(clienteId, produtoId);
            return ResponseEntity.ok(wishlist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Erro ao remover produto: " + e.getMessage());
        }
    }

    @GetMapping("/{clienteId}/produtos")
    public ResponseEntity<?> consultarProdutosDaWishlist(@PathVariable String clienteId) {
        try {
            List<Produto> produtos = wishlistService.consultarProdutosDaWishlist(clienteId);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Wishlist não encontrada ou vazia.")) {
                return ResponseEntity.status(404).body("Wishlist não encontrada ou vazia.");
            }
            return ResponseEntity.status(500).body("Erro ao consultar produtos: " + e.getMessage());
        }
    }
}
