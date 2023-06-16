package com.example.cognito.mercado.persistence;

import com.example.cognito.mercado.persistence.crud.ProductoCrudRepository;
import com.example.cognito.mercado.persistence.entity.Producto;
import com.example.cognito.mercado.persistence.mapper.ProductMapper;
import com.example.cognito.mercado.service.Product;
import com.example.cognito.mercado.service.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//indicamos que esta clase interactua con la base de datos o @component
//repository en terminos de dominio, utilizamos el mapper para poder los productos en termino de product
@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){

         List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
         return mapper.toProducts(productos);
    }
    //return names filter for id categoria
    @Override
    public Optional<List<Product>> getByCategory(int categoryId){
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombre(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }
    //return productos with low numbers of stock
    @Override
    public Optional<List<Product>> getByScarseProducts(int quantity){
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(prods -> mapper.toProducts(prods));
    }
    //return products filter for a price greater
   // public Optional<List<Producto>> getByPrecio(int precio){
     //   return productoCrudRepository.findByPrecioVentaGreaterthanAndEstado(precio,true);
    //}
    @Override
    public Optional<Product> getByProduct(int productId){

        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }
    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }


    @Override
    public void delete(int productId){

        productoCrudRepository.deleteById(productId);
    }



}
