package com.example.cognito.mercado.service.repository;

import com.example.cognito.mercado.service.Purchase;


import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clientId);
    Purchase save(Purchase purchase);


}
