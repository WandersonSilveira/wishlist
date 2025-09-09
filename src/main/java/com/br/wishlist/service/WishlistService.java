package com.br.wishlist.service;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.facade.ProdutoFacade;
import com.br.wishlist.facade.WishlistFacade;
import com.br.wishlist.repository.WishlistRepository;
import com.br.wishlist.validation.WishlistValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
