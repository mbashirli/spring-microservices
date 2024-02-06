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
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductDTO createProduct(ProductRequest productRequest) {
        try {
            Product product = Product.builder()
                    .productName(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .category(productRequest.getCategory())
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

    public void deleteProduct(String productId) {
        log.info("Delete product method called");
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId.toString());
        }
        productRepository.deleteById(productId);
    }

    public ProductDTO updateProduct(String productId, ProductDTO productDTO) {
        log.info("Update product method called");
        AtomicReference<Product> atomicReference = new AtomicReference<>();
        productRepository.findById(productId).ifPresentOrElse(product -> {
            product.setDescription(productDTO.getDescription());
            product.setProductName(productDTO.getProductName());
            product.setPrice(productDTO.getPrice());
            product.setCategory(productDTO.getCategory());
            productRepository.save(product);
            atomicReference.set(product);
        }, () -> {throw new ProductNotFoundException(productId);});

        return productMapper.productToProductDTO(atomicReference.get());
    }

    public ProductDTO getProductById(String productId) {
        log.info("Get product by id method called");
        return productRepository.findById(productId).map(productMapper::productToProductDTO)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public ProductDTO patchProduct(String productId, ProductDTO productDTO) {
        log.info("Patch product method called");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (productDTO.getProductName() != null && !productDTO.getProductName().isBlank()) {
            product.setProductName(productDTO.getProductName());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            product.setDescription(productDTO.getDescription());
        }

        if (productDTO.getCategory() != null && !productDTO.getCategory().isBlank()) {
            product.setCategory(productDTO.getCategory());
        }

        product = productRepository.save(product);
        return productMapper.productToProductDTO(product);
    }
}
