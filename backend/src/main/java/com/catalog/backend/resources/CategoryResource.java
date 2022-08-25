package com.catalog.backend.resources;

import com.catalog.backend.dto.CategoryDTO;
import com.catalog.backend.service.CategoryService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/v1/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping("/list-all")
    public ResponseEntity<List<CategoryDTO>> findAll() {

        List<CategoryDTO> listAllCategories = service.findAll();

        return ResponseEntity.ok().body(listAllCategories);
    }

    @GetMapping("/list-id/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {

        CategoryDTO listCategoryById = service.findById(id);

        return ResponseEntity.ok().body(listCategoryById);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO category) {

        category = service.create(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(category);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO category) {

        category = service.update(id, category);

        return ResponseEntity.ok().body(category);
    }
}
