package com.catalog.backend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.catalog.backend.dto.ProductDTO;
import com.catalog.backend.entities.Category;
import com.catalog.backend.entities.Product;
import com.catalog.backend.factory.Factory;
import com.catalog.backend.repository.CategoryRepository;
import com.catalog.backend.repository.ProductRepository;
import com.catalog.backend.service.exceptions.DatabaseException;
import com.catalog.backend.service.exceptions.IdNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existsId;
    private long idNotExists;
    private long dependentId;

    PageImpl<Product> page;
    Product product;
    Category category;
    ProductDTO dto;

    @BeforeEach
    void setUp() {

        existsId = 1L;
        idNotExists = 1000L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));

        when(productRepository.findAll((Pageable) any())).thenReturn(page);

        when(productRepository.save(any())).thenReturn(product);

        when(productRepository.findById(existsId)).thenReturn(Optional.of(product));
        when(productRepository.findById(idNotExists)).thenReturn(Optional.empty());

        when(productRepository.findAll((Pageable) any())).thenReturn(page);

        when(productRepository.getReferenceById(existsId)).thenReturn(product);
        when(productRepository.getReferenceById(idNotExists)).thenThrow(EntityNotFoundException.class);

        when(categoryRepository.getReferenceById(existsId)).thenReturn(category);
        when(categoryRepository.getReferenceById(idNotExists)).thenThrow(EntityNotFoundException.class);

        doNothing().when(productRepository).deleteById(existsId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(idNotExists);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    void updateShouldReturnProductDTOExisting() {

        dto = Factory.createProductDTO();

        ProductDTO result = service.update(existsId, dto);

        assertNotNull(result);
    }

    @Test
    void updateShouldThrowIdNotFoundExceptionWhenIdNotExist() {

        assertThrows(IdNotFoundException.class, () -> {

            service.update(idNotExists, dto);
        });
    }

    @Test
    void findByIdShouldReturnProductDTOWhenExisting() {

        ProductDTO result = service.findById(existsId);

        assertNotNull(result);
    }

    @Test
    void findByIdShouldThrowIdNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(IdNotFoundException.class, () -> {

            service.findById(idNotExists);
        });
    }

    @Test
    void findAllPagedShouldReturnPageItems() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        assertNotNull(result);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteShouldDoNothingWhenIdExist() {

        assertDoesNotThrow(() -> {

            service.delete(existsId);
        });
    }

    @Test
    void deleteShouldThrowIdNotFoundExceptionWhenIdNotExist() {

        assertThrows(IdNotFoundException.class, () -> {

            service.delete(idNotExists);
        });
    }

    @Test
    void deleteShouldDatabaseExceptionWhenIdExist() {

        assertThrows(DatabaseException.class, () -> {

            service.delete(dependentId);
        });
    }
}
