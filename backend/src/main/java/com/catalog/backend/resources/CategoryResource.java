package com.catalog.backend.resources;

import com.catalog.backend.dto.CategoryDTO;
import com.catalog.backend.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
