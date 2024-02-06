package dp.ms.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for Product")
public class ProductDTO {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "Name")
    private String productName;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Category")
    private String category;

    @Schema(description = "Price")
    private BigDecimal price;
}
