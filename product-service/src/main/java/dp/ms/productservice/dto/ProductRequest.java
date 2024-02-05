package dp.ms.productservice.dto;

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
public class ProductRequest {

    @NotBlank(message = "name may not be blank")
    private String name;

    @NotBlank(message = "description may not be blank")
    private String description;

    @NotNull(message = "price may not be blank")
    private BigDecimal price;
}
