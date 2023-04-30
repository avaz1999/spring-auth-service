package uz.avaz.security.service;

import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.UserDto;

public interface UserService {

    ApiResponse<?> edit(UserDto dto);

    ApiResponse<?> getAllUsers();

    ApiResponse<?> getById(Long id);
}
