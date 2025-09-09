package com.br.wishlist.service;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.facade.ProdutoFacade;
import com.br.wishlist.facade.WishlistFacade;
import com.br.wishlist.repository.WishlistRepository;
import com.br.wishlist.validation.WishlistValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistFacade wishlistFacade;

    @Autowired
    private ProdutoFacade produtoFacade;

    public Wishlist adicionarProdutoNaWishlist(String clienteId, String produtoId) {
        Wishlist wishlist = wishlistFacade.buscarOuCriarWishlist(clienteId);
        Produto produto = produtoFacade.buscarProdutoPorId(produtoId);
        WishlistValidator.validarLimiteProdutos(wishlist);
        if (!WishlistValidator.produtoJaExisteNaWishlist(wishlist, produtoId)) {
            wishlist.getProdutos().add(produto);
        }
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removerProdutoDaWishlist(String clienteId, String produtoId) {
        Wishlist wishlist = wishlistFacade.buscarOuCriarWishlist(clienteId);
        boolean produtoExiste = WishlistValidator.produtoJaExisteNaWishlist(wishlist, produtoId);

        if (!produtoExiste) {
            throw new RuntimeException("Produto não encontrado na wishlist.");
        }

        wishlist.getProdutos().removeIf(p -> p.getId().equals(produtoId));
        return wishlistRepository.save(wishlist);
    }

    public List<Produto> consultarProdutosDaWishlist(String clienteId) {
        Wishlist wishlist = wishlistFacade.buscarOuCriarWishlist(clienteId);

        if (wishlist == null || wishlist.getProdutos() == null || wishlist.getProdutos().isEmpty()) {
            throw new RuntimeException("Wishlist não encontrada ou vazia.");
        }

        return wishlist.getProdutos();
    }
}
