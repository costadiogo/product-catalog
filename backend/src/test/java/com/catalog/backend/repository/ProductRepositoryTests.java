package com.catalog.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.catalog.backend.entities.Product;
import com.catalog.backend.factory.Factory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
class ProductRepositoryTests {

    private long existsId;
    private long idNotExists;
    private long totalProducts;

    @BeforeEach
    void setUp() {

        existsId = 1L;
        idNotExists = 10000L;
        totalProducts = 25L;
    }

    @Autowired
    private ProductRepository repository;

    @Test
    void shouldSavePersistWjthAutoincrementWhenIdIsNull() {

        Product product = Factory.createProduct();

        product.setId(null);

        product = repository.save(product);

        assertNotNull(product.getId());
        assertEquals(totalProducts + 1, product.getId());
    }

    @Test
    void shouldDeleteObjectWhenExistsId() {

        repository.deleteById(existsId);

        Optional<Product> result = repository.findById(existsId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldDeleteThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        assertThrows(EmptyResultDataAccessException.class, () -> {

            repository.deleteById(idNotExists);

        });
    }
}
