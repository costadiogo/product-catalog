package com.catalog.backend.resources;

import com.catalog.backend.dto.ProductDTO;
import com.catalog.backend.service.ProductService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping("/list-all")
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {

        Page<ProductDTO> listCategories = service.findAllPaged(pageable);

        return ResponseEntity.ok().body(listCategories);
    }

    @GetMapping("/list-id/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

        ProductDTO listProductsById = service.findById(id);

        return ResponseEntity.ok().body(listProductsById);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO category) {

        category = service.create(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(category);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO category) {

        category = service.update(id, category);

        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
