package com.catalog.backend.service;

import com.catalog.backend.dto.CategoryDTO;
import com.catalog.backend.dto.ProductDTO;
import com.catalog.backend.entities.Category;
import com.catalog.backend.entities.Product;
import com.catalog.backend.repository.CategoryRepository;
import com.catalog.backend.repository.ProductRepository;
import com.catalog.backend.service.exceptions.DatabaseException;
import com.catalog.backend.service.exceptions.IdNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {

        Page<Product> products = productRepository.findAll(pageRequest);

        return products.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Optional<Product> obj = productRepository.findById(id);

        Product entity = obj.orElseThrow(() -> new IdNotFoundException("Id not found."));

        return new ProductDTO(entity, entity.getCategories());

    }

    @Transactional
    public ProductDTO create(ProductDTO product) {

        Product entity = new Product();

        inputDataDtoToEntity(product, entity);

        entity = productRepository.save(entity);

        return new ProductDTO(entity);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO product) {

        try {
            Product entity = productRepository.getReferenceById(id);

            inputDataDtoToEntity(product, entity);

            entity = productRepository.save(entity);

            return new ProductDTO(entity);

        } catch (EntityNotFoundException ex) {

            throw new IdNotFoundException("Id Not Found " + id);

        }

    }

    public void delete(Long id) {

        try {
            productRepository.deleteById(id);

        } catch (EmptyResultDataAccessException ex) {
            throw new IdNotFoundException("Id Not Found " + id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Integrity Violation of database" + ex);
        }
    }

    private void inputDataDtoToEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();

        for (CategoryDTO categoryDto : dto.getCategories()) {

            Category category = categoryRepository.getReferenceById(categoryDto.getId());

            entity.getCategories().add(category);
        }
    }
}
