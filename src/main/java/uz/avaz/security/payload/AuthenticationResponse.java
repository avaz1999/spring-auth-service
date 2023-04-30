package uz.avaz.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@NonNull
@AllArgsConstructor
@Data
@Builder
public class AuthenticationResponse {
    private String token;
}
