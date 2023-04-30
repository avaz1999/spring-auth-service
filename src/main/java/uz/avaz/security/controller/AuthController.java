package uz.avaz.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.AuthenticationRequestDto;
import uz.avaz.security.dto.RegisterRequestDto;
import uz.avaz.security.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?>  register(@RequestBody RegisterRequestDto request){
        return ApiResponse.controller(authenticationService.register(request));
    }
    @PostMapping("/authentication")
    public ResponseEntity<?>  register(@RequestBody AuthenticationRequestDto request){
    return ApiResponse.controller(authenticationService.authenticate(request));
    }
    @DeleteMapping("/logout/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ApiResponse.controller(authenticationService.logout(id));
    }
}

