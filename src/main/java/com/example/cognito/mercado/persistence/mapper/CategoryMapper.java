package com.example.cognito.mercado.persistence.mapper;

import com.example.cognito.mercado.persistence.entity.Categoria;
import com.example.cognito.mercado.service.Category;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //convierte categorias(entity) en category(domain)
    @Mappings({
            @Mapping(source = "idCategoria",target = "categoryId"),
            @Mapping(source = "descripcion",target = "category"),
            @Mapping(source = "estado",target = "active"),

    })
    Category toCategory(Categoria categoria);
    //hace mapeo inverso al que se definio
    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true)
    Categoria toCategoria(Category category);
}
