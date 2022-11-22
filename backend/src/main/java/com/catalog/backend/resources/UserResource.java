package com.catalog.backend.resources;

import com.catalog.backend.dto.UserDTO;
import com.catalog.backend.dto.UserInsertDTO;
import com.catalog.backend.service.UserService;
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
@RequestMapping(value = "/v1/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping("/list-all")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {

        Page<UserDTO> listCategories = service.findAllPaged(pageable);

        return ResponseEntity.ok().body(listCategories);
    }

    @GetMapping("/list-id/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

        UserDTO listUsersById = service.findById(id);

        return ResponseEntity.ok().body(listUsersById);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserInsertDTO user) {

        UserDTO dto = service.create(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {

        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
