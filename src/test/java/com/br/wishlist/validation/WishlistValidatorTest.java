package com.br.wishlist.validation;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.entity.Wishlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class WishlistValidatorTest {

    @Test
    void deveRetornarTrueQuandoProdutoJaExisteNaWishlist() {
        Produto produto = new Produto();
        produto.setId("1");
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        Wishlist wishlist = new Wishlist();
        wishlist.setProdutos(produtos);
        boolean existe = WishlistValidator.produtoJaExisteNaWishlist(wishlist, "1");
        Assertions.assertTrue(existe);
    }

    @Test
    void deveRetornarFalseQuandoProdutoNaoExisteNaWishlist() {
        Produto produto = new Produto();
        produto.setId("1");
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        Wishlist wishlist = new Wishlist();
        wishlist.setProdutos(produtos);
        boolean existe = WishlistValidator.produtoJaExisteNaWishlist(wishlist, "2");
        Assertions.assertFalse(existe);
    }

    @Test
    void deveLancarExcecaoQuandoLimiteDeProdutosAtingido() {
        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Produto produto = new Produto();
            produto.setId(String.valueOf(i));
            produtos.add(produto);
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setProdutos(produtos);
        Assertions.assertThrows(RuntimeException.class, () -> {
            WishlistValidator.validarLimiteProdutos(wishlist);
        });
    }

    @Test
    void naoDeveLancarExcecaoQuandoLimiteDeProdutosNaoAtingido() {
        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            Produto produto = new Produto();
            produto.setId(String.valueOf(i));
            produtos.add(produto);
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setProdutos(produtos);
        Assertions.assertDoesNotThrow(() -> {
            WishlistValidator.validarLimiteProdutos(wishlist);
        });
    }
}

