package com.example.cognito.mercado.persistence.crud;

import com.example.cognito.mercado.persistence.entity.Producto;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
    //@Qu ery(value = "SELECT *  FROM productos WHERE id_categoria = ?", nativeQuery = true)

    List<Producto> findByIdCategoriaOrderByNombre(int idCategoria);

    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

   // Optional<List<Producto>> findByPrecioVentaGreaterthanAndEstado(double precioVenta,boolean estado);

}
