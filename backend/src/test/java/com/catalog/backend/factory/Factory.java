package com.catalog.backend.factory;

import com.catalog.backend.dto.ProductDTO;
import com.catalog.backend.entities.Category;
import com.catalog.backend.entities.Product;
import java.time.Instant;

public class Factory {

    public static Product createProduct() {

        Product product = new Product(1L, "Head Phone Beats", "Com 40 horas de bateria, o Beats Solo3 Wireless é o fone de ouvido "
            + "perfeito "
            + "para o dia a dia, com o Fast Fuel, uma recarga de cinco minutos oferece três horas de música. Curta toda sua liberdade de "
            + "movimento e o famoso som da Beats com a tecnologia wireless Bluetooth Classe 1. Os fones de revestimento acolchoado podem "
            + "ser ajustados para que você tenha conforto o dia todo.", 1515.7,
            "https://www.apple.com/br/shop/accessories/all/beats",
            Instant.now());

        product.getCategories().add(new Category(2L, "Electronics"));

        return product;
    }

    public static ProductDTO createProductDTO() {

        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
