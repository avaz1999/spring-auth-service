package uz.avaz.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.service.FileService;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("info/{id}")
    public ResponseEntity<?> getForInfo(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.getFormInfo(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.delete(id));
    }


}
