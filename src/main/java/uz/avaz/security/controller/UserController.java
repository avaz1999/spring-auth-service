package uz.avaz.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.UserDto;
import uz.avaz.security.service.FileService;
import uz.avaz.security.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    @PostMapping("/upload-img/{userId}")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file")MultipartFile file,@PathVariable Long userId){
        return ApiResponse.controller(fileService.upload(file,userId));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserDto dto){
        return ApiResponse.controller(userService.edit(dto));
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        return ApiResponse.controller(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ApiResponse.controller(userService.getById(id));
    }
}
