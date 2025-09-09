package com.br.wishlist.controller;

import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    void deveAdicionarProdutoNaWishlistComSucesso() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setId("w1");
        wishlist.setClienteId("c1");
        when(wishlistService.adicionarProdutoNaWishlist(anyString(), anyString())).thenReturn(wishlist);

        mockMvc.perform(post("/api/wishlist/c1/produtos/p1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("w1"))
                .andExpect(jsonPath("$.clienteId").value("c1"));
    }

    @Test
    void deveRetornarErroAoAdicionarProdutoQuandoServiceLancaExcecao() throws Exception {
        when(wishlistService.adicionarProdutoNaWishlist(anyString(), anyString()))
                .thenThrow(new RuntimeException("Erro ao adicionar produto"));

        mockMvc.perform(post("/api/wishlist/c1/produtos/p1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
