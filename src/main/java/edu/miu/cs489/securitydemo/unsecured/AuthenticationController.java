package edu.miu.cs489.securitydemo.unsecured;

import edu.miu.cs489.securitydemo.auth.service.AuthenticationService;
import edu.miu.cs489.securitydemo.auth.request.AuthenticationRequestDTO;
import edu.miu.cs489.securitydemo.auth.request.RegisterRequestDTO;
import edu.miu.cs489.securitydemo.auth.response.AuthenticationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponseDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(authenticationRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponseDTO);
    }


}
