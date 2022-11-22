package com.catalog.backend.service;

import com.catalog.backend.dto.RoleDTO;
import com.catalog.backend.dto.UserDTO;
import com.catalog.backend.dto.UserInsertDTO;
import com.catalog.backend.entities.Role;
import com.catalog.backend.entities.User;
import com.catalog.backend.repository.RoleRepository;
import com.catalog.backend.repository.UserRepository;
import com.catalog.backend.service.exceptions.DatabaseException;
import com.catalog.backend.service.exceptions.IdNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {

        Page<User> products = userRepository.findAll(pageable);

        return products.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {

        Optional<User> obj = userRepository.findById(id);

        User entity = obj.orElseThrow(() -> new IdNotFoundException("Id not found."));

        return new UserDTO(entity);

    }

    @Transactional
    public UserDTO create(UserInsertDTO dto) {

        User entity = new User();

        inputDataDtoToEntity(dto, entity);

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        entity = userRepository.save(entity);

        return new UserDTO(entity);

    }

    @Transactional
    public UserDTO update(Long id, UserDTO product) {

        try {
            User entity = userRepository.getReferenceById(id);

            inputDataDtoToEntity(product, entity);

            entity = userRepository.save(entity);

            return new UserDTO(entity);

        } catch (EntityNotFoundException ex) {

            throw new IdNotFoundException("Id Not Found " + id);

        }

    }

    public void delete(Long id) {

        try {
            userRepository.deleteById(id);

        } catch (EmptyResultDataAccessException ex) {
            throw new IdNotFoundException("Id Not Found " + id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Integrity Violation of database" + ex);
        }
    }

    /**
     * <ul>
     *     <li>Method used to associate a user to one or more roles.</li>
     *     <li>Used in contracts to create and update a roles.</li>
     * </ul>
     *
     */

    private void inputDataDtoToEntity(UserDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO roleDTO : dto.getRoles()) {

            Role role = roleRepository.getReferenceById(roleDTO.getId());

            entity.getRoles().add(role);
        }
    }
}
