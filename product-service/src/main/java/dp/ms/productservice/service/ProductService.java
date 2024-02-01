package dp.ms.productservice.service;

import dp.ms.productservice.dto.ProductRequest;
import dp.ms.productservice.dto.ProductResponse;
import dp.ms.productservice.mappers.ProductMapper;
import dp.ms.productservice.model.Product;
import dp.ms.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        Product savedProduct = productRepository.save(product);

        log.info("Product with id - {} is saved.", savedProduct.getId());
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Get all products method called");
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }
}
