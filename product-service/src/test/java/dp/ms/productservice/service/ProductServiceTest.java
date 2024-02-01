package dp.ms.productservice.service;

import dp.ms.productservice.dto.ProductRequest;
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
    void createProduct() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("Product")
                .description("Desc")
                .price(BigDecimal.valueOf(123))
                .build();

        Product product = Product.builder()
                .id("22")
                .build();

        given(productRepository.save(any(Product.class))).willReturn(product);
        productService.createProduct(productRequest);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getAllProducts() {
    }
}