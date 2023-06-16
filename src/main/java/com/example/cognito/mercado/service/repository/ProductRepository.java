package com.example.cognito.mercado.service.repository;

import com.example.cognito.mercado.service.Product;


import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getByScarseProducts(int quantity);
    Optional<Product> getByProduct(int productId);
    Product save(Product product);



    void delete(int productId);

}
