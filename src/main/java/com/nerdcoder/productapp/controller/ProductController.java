package com.nerdcoder.productapp.controller;

import com.nerdcoder.productapp.entity.ProductDetails;
import com.nerdcoder.productapp.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Product APIs", description = "add, update, get, delete the product")
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);
  private final ProductServiceImpl productServiceImpl;

  public ProductController(ProductServiceImpl productServiceImpl) {
    this.productServiceImpl = productServiceImpl;
  }

  @PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ProductDetails saveProductDetails(@RequestBody ProductDetails productDetails) {
    log.info("product save request received");
    return productServiceImpl.saveProduct(productDetails);
  }
}
