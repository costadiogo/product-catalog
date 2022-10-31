package com.catalog.backend.resources;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.catalog.backend.dto.ProductDTO;
import com.catalog.backend.factory.Factory;
import com.catalog.backend.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ProductResource.class)
class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    ProductDTO dto;
    PageImpl<ProductDTO> page;

    @BeforeEach
    void setUp() {

        dto = Factory.createProductDTO();
        page = new PageImpl<>(List.of(dto));

        when(service.findAllPaged(any())).thenReturn(page);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("v1/products")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
