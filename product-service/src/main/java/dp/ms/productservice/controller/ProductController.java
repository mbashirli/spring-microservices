package dp.ms.productservice.controller;

import dp.ms.productservice.dto.CreateProductRequest;
import dp.ms.productservice.dto.ProductDTO;
import dp.ms.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Internal api", description = "Product service internal controller")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(description = "Returns a list of all products in the system.")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if the list is empty
        } else {
            return ResponseEntity.ok(products); // Return 200 OK with the list of products
        }
    }

    @GetMapping("/{productId}")
    @Operation(description = "Returns the product with the given id.")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") String productId){
        ProductDTO foundProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(foundProduct);
    }

    @GetMapping("/{productId}/select-fields")
    @Operation(description = "Returns the product with the given id.")
    public ResponseEntity<Map<String, Object>> getProductFieldsById(@PathVariable("productId") String productId, @RequestParam String fields){
        Map<String, Object> foundProductFields = productService.getProductFieldsById(productId, fields);
        return ResponseEntity.status(HttpStatus.OK).body(foundProductFields);
    }

    @PostMapping()
    @Operation(description = "Creates a new product using the sent request body.")
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody CreateProductRequest createProductRequest,
                                                    @RequestParam Integer stockQuantity) {
        ProductDTO savedDto = productService.createProduct(createProductRequest, stockQuantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @DeleteMapping("/{productId}")
    @Operation(description = "Deletes a product with the given id.")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
        String message = "Product with id " + productId + " has been deleted.";
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    @PutMapping("/{productId}")
    @Operation(description = "Completely updates a product with the given request body.")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") String productId, @Validated @RequestBody  ProductDTO productDTO){
        ProductDTO updatedDTO = productService.updateProduct(productId, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDTO);
    }

    @PatchMapping("/{productId}")
    @Operation(description = "Patch the product with the given request body.")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable("productId") String productId, @RequestBody  ProductDTO productDTO){
        ProductDTO patchedDTO = productService.patchProduct(productId, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(patchedDTO);
    }
}
