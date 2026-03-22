package com.nerdcoder.jeelax.repository;

import com.nerdcoder.jeelax.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductDetails, Integer> {

  @Query("SELECT MAX(p.id) FROM ProductDetails p")
  Integer fetchLastProduct();

  ProductDetails findTopByOrderByIdDesc();
}
