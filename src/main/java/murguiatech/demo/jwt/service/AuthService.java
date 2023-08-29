package murguiatech.demo.jwt.service;

import lombok.RequiredArgsConstructor;
import murguiatech.demo.jwt.model.*;
import murguiatech.demo.jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    public AuthResponseToken login(LoginRequest request) {
        return null;
    }

    public AuthResponseToken register(RegisterRequest request) {
        User user = new User()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER);
        userRepository.save(user);
        //jwtservice not made yet
        return AuthResponseToken.builder().token(null).build();
    }
}
