package ie.atu.week8.projectexercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void testGetProductById() throws Exception {
        Product p = new Product(1L, "name", "desc", 100);
        when(productService.getProductById(1L)).thenReturn(Optional.of(p));


        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void createProduct() throws Exception {
        Product p = new Product(2L, "Prod A", "A non living thing", 200);
        when(productService.saveProduct(any(Product.class))).thenReturn(p);


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(p);
        mockMvc.perform(post("/products").contentType("application/json").content(jsonValue))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Prod A"));
    }

    @Test
    void updateProduct() throws Exception {
        Product existingP = new Product(1L, "Prod A", "A non living thing", 200);
        Product updatedP = new Product(1L, "Prod AB", "AB non living thing", 200);

        when(productService.getProductById(1L)).thenReturn(Optional.of(existingP));
        when(productService.saveProduct(any(Product.class))).thenReturn(updatedP);

        objectMapper = new ObjectMapper();
        String updatedProductJson = objectMapper.writeValueAsString(updatedP);

        mockMvc.perform(put("/products/1").contentType("application/json").content(updatedProductJson))
                .andExpect(jsonPath("$.name").value("Prod AB"))
                .andExpect(jsonPath("$.description").value("AB non living thing"))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    void deleteProduct() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(new Product()));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}