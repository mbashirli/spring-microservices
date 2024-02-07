package dp.ms.productservice.mappers;

import dp.ms.productservice.dto.ProductDTO;
import dp.ms.productservice.dto.ProductRequest;
import dp.ms.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);

    Product productRequestToProduct(ProductRequest productRequest);
}
