package uz.avaz.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.payload.AuthenticationResponse;
import uz.avaz.security.jwt_security.JwtService;
import uz.avaz.security.dto.AuthenticationRequestDto;
import uz.avaz.security.dto.RegisterRequestDto;
import uz.avaz.security.entity.User;
import uz.avaz.security.entity.enums.Role;
import uz.avaz.security.payload.ResponseMessage;
import uz.avaz.security.repository.UserRepo;
import uz.avaz.security.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<?> register(RegisterRequestDto request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new ApiResponse<>(true,ResponseMessage.SUCCESS,AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    @Override
    public ApiResponse<?> authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new ApiResponse<>(true, ResponseMessage.SUCCESS, AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    @Override
    public ApiResponse<?> logout(Long id) {
       try {
           if (id != null){
               Optional<User> byId = userRepo.findById(id);
               if (byId.isPresent()) {
                   userRepo.deleteById(id);
                   return new ApiResponse<>(true,ResponseMessage.SUCCESS);
               }
           }else return new ApiResponse<>(false,ResponseMessage.OBJECT_IS_NULL);
       }catch (Throwable e){
           e.printStackTrace();
       }
        return new ApiResponse<>(false,ResponseMessage.SERVER_ERROR);
    }
}
