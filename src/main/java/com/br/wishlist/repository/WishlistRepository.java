package com.br.wishlist.repository;

import com.br.wishlist.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByClienteId(String clienteId);
}
