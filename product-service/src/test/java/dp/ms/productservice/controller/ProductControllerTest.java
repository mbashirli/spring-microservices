package dp.ms.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dp.ms.productservice.dto.CreateProductRequest;
import dp.ms.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService; // Assuming ProductService is used in your controller

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createProductConstraintViolation() throws Exception {
        CreateProductRequest createProductRequest = new CreateProductRequest(); // fill with invalid data

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductRequest)))
                .andExpect(status().isBadRequest()); // or other appropriate status
    }

//    @Test
//    public void createProductSuccess() throws Exception{
//        CreateProductRequest createProductRequest = CreateProductRequest.builder()
//                .productName("iPhone")
//                .price(BigDecimal.TEN)
//                .description("Description")
//                .category("category")
//                .build();
//
//        mockMvc.perform(post("/api/product")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(createProductRequest)))
//                .andExpect(status().isCreated());
//    }

}
