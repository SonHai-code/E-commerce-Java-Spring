package com.sonhai.repository;

import com.sonhai.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long > {
}
