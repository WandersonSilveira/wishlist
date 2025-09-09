package com.br.wishlist.service;

import com.br.wishlist.entity.Produto;
import com.br.wishlist.entity.Wishlist;
import com.br.wishlist.facade.ProdutoFacade;
import com.br.wishlist.facade.WishlistFacade;
import com.br.wishlist.repository.ProdutoRepository;
import com.br.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {
    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private WishlistFacade wishlistFacade;
    @Mock
    private ProdutoFacade produtoFacade;
    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAdicionarProdutoNaWishlist() {
        String clienteId = "1";
        String produtoId = "p1";
        Produto produto = new Produto();
        produto.setId(produtoId);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(new ArrayList<>());
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(produtoFacade.buscarProdutoPorId(produtoId)).thenReturn(produto);
        when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Wishlist result = wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId);
        assertEquals(1, result.getProdutos().size());
        assertEquals(produtoId, result.getProdutos().get(0).getId());
    }

    @Test
    void naoDeveAdicionarProdutoDuplicadoNaWishlist() {
        String clienteId = "1";
        String produtoId = "p1";
        Produto produto = new Produto();
        produto.setId(produtoId);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        wishlist.setProdutos(produtos);
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(produtoFacade.buscarProdutoPorId(produtoId)).thenReturn(produto);
        when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Wishlist result = wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId);
        assertEquals(1, result.getProdutos().size());
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoEncontrado() {
        String clienteId = "1";
        String produtoId = "p1";
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(new ArrayList<>());
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(produtoFacade.buscarProdutoPorId(produtoId)).thenThrow(new RuntimeException("Produto não encontrado"));
        assertThrows(RuntimeException.class, () -> wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId));
    }

    @Test
    void deveLancarExcecaoSeLimiteDeProdutosAtingido() {
        String clienteId = "1";
        String produtoId = "p21";
        Produto produto = new Produto();
        produto.setId(produtoId);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        ArrayList<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Produto p = new Produto();
            p.setId("p" + i);
            produtos.add(p);
        }
        wishlist.setProdutos(produtos);
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(produtoFacade.buscarProdutoPorId(produtoId)).thenReturn(produto);
        assertThrows(RuntimeException.class, () -> wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId));
    }

    @Test
    void deveRemoverProdutoDaWishlist() {
        String clienteId = "1";
        String produtoId = "p1";
        Produto produto = new Produto();
        produto.setId(produtoId);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        wishlist.setProdutos(produtos);
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Wishlist result = wishlistService.removerProdutoDaWishlist(clienteId, produtoId);
        assertTrue(result.getProdutos().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoRemoverProdutoInexistente() {
        String clienteId = "1";
        String produtoId = "p1";
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(new ArrayList<>());
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        assertThrows(RuntimeException.class, () -> wishlistService.removerProdutoDaWishlist(clienteId, produtoId));
    }

    @Test
    void deveRemoverApenasProdutoCorretoDaWishlist() {
        String clienteId = "1";
        Produto produto1 = new Produto();
        produto1.setId("p1");
        Produto produto2 = new Produto();
        produto2.setId("p2");
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(produtos);
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Wishlist result = wishlistService.removerProdutoDaWishlist(clienteId, "p1");
        assertEquals(1, result.getProdutos().size());
        assertEquals("p2", result.getProdutos().get(0).getId());
    }

    @Test
    void deveConsultarProdutosDaWishlistComSucesso() {
        String clienteId = "1";
        Produto produto1 = new Produto();
        produto1.setId("p1");
        Produto produto2 = new Produto();
        produto2.setId("p2");
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(produtos);
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        var result = wishlistService.consultarProdutosDaWishlist(clienteId);
        assertEquals(2, result.size());
        assertEquals("p1", result.get(0).getId());
        assertEquals("p2", result.get(1).getId());
    }

    @Test
    void deveLancarExcecaoAoConsultarProdutosQuandoWishlistVazia() {
        String clienteId = "1";
        Wishlist wishlist = new Wishlist();
        wishlist.setClienteId(clienteId);
        wishlist.setProdutos(new ArrayList<>());
        when(wishlistFacade.buscarOuCriarWishlist(clienteId)).thenReturn(wishlist);
        assertThrows(RuntimeException.class, () -> wishlistService.consultarProdutosDaWishlist(clienteId));
    }
}
