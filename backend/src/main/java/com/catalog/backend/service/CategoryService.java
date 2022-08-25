package com.catalog.backend.service;

import com.catalog.backend.dto.CategoryDTO;
import com.catalog.backend.entities.Category;
import com.catalog.backend.repository.CategoryRepository;
import com.catalog.backend.service.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = repository.findAll();

        return categoryList.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {

        Optional<Category> obj = repository.findById(id);

        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Id not found."));

        return new CategoryDTO(entity);

    }

    @Transactional(readOnly = true)
    public CategoryDTO create(CategoryDTO category) {

        Category entity = new Category();

        entity.setName(category.getName());

        entity = repository.save(entity);

        return new CategoryDTO(entity);

    }
}
