package uz.avaz.security.service;

import uz.avaz.security.ApiResponse;
import uz.avaz.security.payload.AuthenticationResponse;
import uz.avaz.security.dto.AuthenticationRequestDto;
import uz.avaz.security.dto.RegisterRequestDto;

public interface AuthService {
    public ApiResponse<?> register(RegisterRequestDto request);
    public ApiResponse<?> authenticate(AuthenticationRequestDto request);
    ApiResponse<?> logout(Long id);
}
