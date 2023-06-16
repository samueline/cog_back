package com.example.cognito.mercado.persistence;

import com.example.cognito.mercado.persistence.crud.CompraCrudRepository;
import com.example.cognito.mercado.persistence.entity.Compra;
import com.example.cognito.mercado.persistence.mapper.PurchaseMapper;
import com.example.cognito.mercado.service.Purchase;
import com.example.cognito.mercado.service.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        List<Compra> productos = (List<Compra>) compraCrudRepository.findAll();
        return mapper.toPurchases(productos);
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        //por el foreach indicamos a que compra pertenece el producto
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
