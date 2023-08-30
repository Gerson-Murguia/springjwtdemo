package murguiatech.demo.jwt.service;

import lombok.RequiredArgsConstructor;
import murguiatech.demo.jwt.jwt.JwtService;
import murguiatech.demo.jwt.model.*;
import murguiatech.demo.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponseToken login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponseToken.builder()
                .token(token)
                .build();
    }

    public AuthResponseToken register(RegisterRequest request) {
        User user = new User()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER);
        userRepository.save(user);

        return AuthResponseToken.builder().token(jwtService.getToken(user)).build();
    }
}
