package dp.ms.productservice.service;

import dp.ms.productservice.dto.ProductDTO;
import dp.ms.productservice.dto.ProductRequest;
import dp.ms.productservice.exception.ProductCreationException;
import dp.ms.productservice.exception.ProductNotFoundException;
import dp.ms.productservice.mappers.ProductMapper;
import dp.ms.productservice.model.Product;
import dp.ms.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductDTO createProduct(ProductRequest productRequest) {
        try {
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();
            Product savedProduct = productRepository.save(product);
            log.info("Product with id - {} is saved.", savedProduct.getId());
            return productMapper.productToProductDTO(savedProduct);
        } catch (Exception ex){
            throw new ProductCreationException("Failed to create new product: " + ex.getMessage());
        }
    }

    public List<ProductDTO> getAllProducts() {
        log.info("Get all products method called");
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDTO)
                .toList();
    }

    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId.toString());
        }
        productRepository.deleteById(productId);
    }
}
