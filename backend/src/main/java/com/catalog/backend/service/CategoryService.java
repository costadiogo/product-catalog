package com.catalog.backend.service;

import com.catalog.backend.dto.CategoryDTO;
import com.catalog.backend.entities.Category;
import com.catalog.backend.repository.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {

        Page<Category> categoryList = repository.findAll(pageRequest);

        return categoryList.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {

        Optional<Category> obj = repository.findById(id);

        Category entity = obj.orElseThrow(() -> new IdNotFoundException("Id not found."));

        return new CategoryDTO(entity);

    }

    @Transactional
    public CategoryDTO create(CategoryDTO category) {

        Category entity = new Category();

        entity.setName(category.getName());

        entity = repository.save(entity);

        return new CategoryDTO(entity);

    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO category) {

        try {
            Category entity = repository.getReferenceById(id);

            entity.setName(category.getName());

            entity = repository.save(entity);

            return new CategoryDTO(entity);

        } catch (EntityNotFoundException ex) {

            throw new IdNotFoundException("Id Not Found " + id);

        }

    }

    public void delete(Long id) {

        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException ex) {
            throw new IdNotFoundException("Id Not Found " + id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Integrity Violation of database" + ex);
        }
    }
}
