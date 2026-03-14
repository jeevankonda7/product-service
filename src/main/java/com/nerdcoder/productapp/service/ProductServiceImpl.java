package com.nerdcoder.productapp.service;

import com.nerdcoder.productapp.entity.ProductDetails;
import com.nerdcoder.productapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

  private final ProductRepository productRepository;

  private static String productId = null;


  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDetails saveProduct(ProductDetails productDetails) {
    generateProductId(productDetails);
    return productDetails;
  }

  private void generateProductId(ProductDetails product) {
    if (productId == null) {
      productId = "prod".toUpperCase().concat("-").concat(String.format("%04d",0)).concat("1");
    }
    else {
      int number = Integer.parseInt(productId.replaceAll("[^0-9]",""));
      ++number;
      productId = "prod".toUpperCase().concat("-").concat(String.format("%04d", 0)).concat(String.valueOf(number));
    }
    product.setProductId(productId);
    productRepository.save(product);
  }

  @Override
  public ProductDetails updateProduct(ProductDetails product) {
    return null;
  }

  @Override
  public Optional<ProductDetails> getProductById(Integer id) {
    return Optional.empty();
  }

  @Override
  public Optional<ProductDetails> getProductByProductId(String productId) {
    return Optional.empty();
  }

  @Override
  public Integer deleteById(Integer id) {
    return null;
  }

  @Override
  public Integer fetchMaxIdOfProducts() {
    return null;
  }



}
