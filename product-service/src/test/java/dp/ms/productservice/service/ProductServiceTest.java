package dp.ms.productservice.service;

import dp.ms.productservice.dto.ProductRequest;
import dp.ms.productservice.exception.ProductCreationException;
import dp.ms.productservice.mappers.ProductMapper;
import dp.ms.productservice.model.Product;
import dp.ms.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @Test
    void createProductCreationException() {
        // Set up the mock behavior
        given(productRepository.save(any(Product.class))).willThrow(new RuntimeException("Database error"));

        // Now, test that the exception is thrown
        assertThrows(ProductCreationException.class, () -> {
            productService.createProduct(getProduct());
        });
    }

    private ProductRequest getProduct() {
        return new ProductRequest("Sample Name", "Sample Description", "Sample category", new BigDecimal("99.99"));
    }
}