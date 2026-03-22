package com.nerdcoder.jeelax.service;

import com.nerdcoder.jeelax.entity.ProductDetails;

import java.util.Optional;

public interface ProductService {

   ProductDetails saveProduct(ProductDetails product);
   ProductDetails updateProduct(ProductDetails product);
   Optional<ProductDetails> getProductById(Integer id);
   Optional<ProductDetails> getProductByProductId(String productId);
   Integer deleteById(Integer id);
   Integer fetchMaxIdOfProducts();


}
