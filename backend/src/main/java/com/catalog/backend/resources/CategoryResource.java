package com.catalog.backend.resources;

import com.catalog.backend.entities.Category;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/categories")
public class CategoryResource {

    @GetMapping("/list-all")
    public ResponseEntity<List<Category>> findAll() {

        List<Category> list = new ArrayList<>(0);

        list.add(new Category(1L, "Books"));
        list.add(new Category(2L, "Electronics"));

        return ResponseEntity.ok().body(list);
    }
}
