package dp.ms.authenticationservice.dto;

import dp.ms.authenticationservice.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;
}
