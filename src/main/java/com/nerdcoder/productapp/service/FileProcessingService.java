package com.nerdcoder.productapp.service;

import com.nerdcoder.productapp.entity.ProductDetails;
import com.nerdcoder.productapp.model.ProductsInventoryCsvRepresentation;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileProcessingService {


  @Autowired
  private Executor virtualThreadExecutor;

  private final Object fileLock = new Object();

  public Set<ProductDetails> parseCsv(MultipartFile file) throws Exception {

    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      HeaderColumnNameMappingStrategy<ProductsInventoryCsvRepresentation> productFileHeadersStrategy =
              new HeaderColumnNameMappingStrategy<>();
      productFileHeadersStrategy.setType(ProductsInventoryCsvRepresentation.class);
      CsvToBean<ProductsInventoryCsvRepresentation> csvToBean =
              new CsvToBeanBuilder<ProductsInventoryCsvRepresentation>(reader)
                      .withMappingStrategy(productFileHeadersStrategy)
                      .withIgnoreEmptyLine(true)
                      .withIgnoreLeadingWhiteSpace(true)
                      .build();

      return csvToBean.parse()
              .stream()
              .map(product -> ProductDetails.builder()
                      .productId(product.getProductId())
                      .productName(product.getProductName())
                      .price(product.getProductPrice())
                      .manufacturer(product.getManufacturer())
                      .build())
              .collect(Collectors.toSet());

    }
  }

//  public void writeRecordsToCsv(final List<ProductDetails> productsList) {
//    virtualThreadExecutor.execute(() -> {
//      Path path = Path.of("reports/saved_products_details.csv");
//      try {
//        boolean fileExists = Files.exists(path);
//        if (!fileExists) {
//          Files.createFile(path);
//        }
//        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile(), true))) {
//          if (!fileExists) {
//            String[] headers = new String[]{"productId", "productName", "productPrice", "productManufacturer"};
//            writer.writeNext(headers);
//          }
//          List<String[]> productDetails = productsList.stream()
//                  .map(productData -> new String[]{
//                          productData.getProductId(),
//                          productData.getProductName(),
//                          String.valueOf(productData.getPrice()),
//                          productData.getManufacturer()
//                  }).toList();
//          writer.writeAll(productDetails);
//        }
//      } catch (IOException e) {
//        log.error("unable to create file");
//      }
//
//    });
//  }

  public void writeRecordsToCsv(final List<ProductDetails> productsList) {
    virtualThreadExecutor.execute(() -> {
      synchronized (fileLock) {
        writeToFile(productsList);
      }
    });
  }

  private void writeToFile(List<ProductDetails> productsList) {
    Path path = Path.of("reports/saved_products_details.csv");
    try {
      Files.createDirectories(path.getParent()); // ensure folder exists

      boolean fileExists = Files.exists(path);
      if (!fileExists) {
        Files.createFile(path);
      }

      try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile(), true))) {
        if (!fileExists) {
          writer.writeNext(new String[]{"productId", "productName", "productPrice", "productManufacturer"});
        }

        List<String[]> productDetails = productsList.stream()
                .map(p -> new String[]{
                        p.getProductId(),
                        p.getProductName(),
                        String.valueOf(p.getPrice()),
                        p.getManufacturer()
                }).toList();

        writer.writeAll(productDetails);
      }

    } catch (IOException e) {
      log.error("unable to create file", e);
    }
  }


}
