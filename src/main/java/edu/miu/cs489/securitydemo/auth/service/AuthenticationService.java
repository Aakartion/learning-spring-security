package edu.miu.cs489.securitydemo.auth.service;

import edu.miu.cs489.securitydemo.auth.request.AuthenticationRequestDTO;
import edu.miu.cs489.securitydemo.auth.request.RegisterRequestDTO;
import edu.miu.cs489.securitydemo.auth.response.AuthenticationResponseDTO;
import edu.miu.cs489.securitydemo.config.ApplicationConfiguration;
import edu.miu.cs489.securitydemo.config.JwtService;
import edu.miu.cs489.securitydemo.user.User;
import edu.miu.cs489.securitydemo.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO){
//        Construct user object from registerRequestDTO
        User user = new User(
                registerRequestDTO.firstName(),
                registerRequestDTO.lastName(),
                registerRequestDTO.username(),
                passwordEncoder.encode(registerRequestDTO.password()),
                registerRequestDTO.role()
        );
        User registeredUser = userRepository.save(user);
//        Generate the token
        String token = jwtService.generateToken(registeredUser);
        return new AuthenticationResponseDTO(token);
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.username(),
                        authenticationRequestDTO.password()
                )
        );
//        Now Authentication is successfull or not
//        Next-> Generate  token for this authenticated user
//        User user = userRepository.findByUsername(authenticationRequestDTO.username()).orElseThrow(()->new UsernameNotFoundException("Not Found"));
//        Principal principal = (Principal) authentication.getPrincipal();
//        User user = (User) principal;

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponseDTO(token);
    }
}
