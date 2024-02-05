package dp.ms.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for ProductRequest")
public class ProductRequest {

    @Schema(description = "Name")
    @NotBlank(message = "name may not be blank")
    private String name;

    @Schema(description = "Description")
    @NotBlank(message = "description may not be blank")
    private String description;

    @Schema(description = "Price")
    @NotNull(message = "price may not be blank")
    private BigDecimal price;
}
