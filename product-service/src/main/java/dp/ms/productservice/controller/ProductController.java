package dp.ms.productservice.controller;

import dp.ms.productservice.dto.ProductRequest;
import dp.ms.productservice.dto.ProductDTO;
import dp.ms.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if the list is empty
        } else {
            return ResponseEntity.ok(products); // Return 200 OK with the list of products
        }
    }
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductRequest productRequest) {
        ProductDTO savedDto = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
