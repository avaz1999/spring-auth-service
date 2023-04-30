package uz.avaz.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.UserDto;
import uz.avaz.security.entity.User;
import uz.avaz.security.payload.ResponseMessage;
import uz.avaz.security.repository.UserRepo;
import uz.avaz.security.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public ApiResponse<?> edit(UserDto dto) {
        try {
            Optional<User> byId = userRepo.findById(dto.getId());
            if (byId.isPresent()) {
                byId.get().setFirstname(dto.getFirstname() != null ? dto.getFirstname() : byId.get().getFirstname());
                byId.get().setLastname(dto.getLastname() != null ? dto.getLastname() : byId.get().getLastname());
                byId.get().setEmail(dto.getEmail() != null ? dto.getEmail() : byId.get().getEmail());
                byId.get().setPassword(dto.getPassword() != null ? dto.getPassword() : byId.get().getPassword());
                userRepo.save(byId.get());
                return new ApiResponse<>(true, ResponseMessage.SUCCESS);
            } else return new ApiResponse<>(false, ResponseMessage.OBJECT_NOT_FOUND);
        } catch (Throwable e) {
            return new ApiResponse<>(false, ResponseMessage.SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<?> getAllUsers() {
        try {
            return new ApiResponse<>(true,ResponseMessage.SUCCESS,UserDto.toDtoList(userRepo.findAll()));
        }catch (Throwable e){
            e.printStackTrace();
        }
        return new ApiResponse<>(false,ResponseMessage.SERVER_ERROR);
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        try {
            if (id != null){
                Optional<User> byId = userRepo.findById(id);
                if (byId.isPresent()) {
                    UserDto dto = UserDto.toDto(byId.get());
                    return new ApiResponse<>(true,ResponseMessage.SUCCESS,dto);
                }else return new ApiResponse<>(false,ResponseMessage.OBJECT_NOT_FOUND);
            }else return new ApiResponse<>(false,ResponseMessage.OBJECT_IS_NULL);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return new ApiResponse<>(false,ResponseMessage.SERVER_ERROR);
    }
}
