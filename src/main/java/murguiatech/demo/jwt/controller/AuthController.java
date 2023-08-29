package murguiatech.demo.jwt.controller;

import lombok.RequiredArgsConstructor;
import murguiatech.demo.jwt.model.*;
import murguiatech.demo.jwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseToken> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponseToken> register(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(authService.register(request));
    }
}
