package dp.ms.productservice.mappers;

import dp.ms.productservice.dto.ProductResponse;
import dp.ms.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse productToProductResponse(Product product);
}
