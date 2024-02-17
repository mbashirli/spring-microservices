package dp.ms.authenticationservice.controller;

import dp.ms.authenticationservice.dto.LoginDTO;
import dp.ms.authenticationservice.dto.LoginResponseDTO;
import dp.ms.authenticationservice.dto.RegistrationDTO;
import dp.ms.authenticationservice.models.ApplicationUser;
import dp.ms.authenticationservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser register(@RequestBody RegistrationDTO registrationDTO){
        return authenticationService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO){
        return authenticationService.loginUser(loginDTO.getUsername(), loginDTO.getPassword());
    }
}
