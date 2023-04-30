package uz.avaz.security.service;

import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.CustomResourceWrapper;
import uz.avaz.security.dto.FileDto;

import java.util.List;

public interface FileService {
    String GLOBAL = "./static";
    String FILE = GLOBAL + "/file";

    ApiResponse<?> upload (MultipartFile file,Long userId);
    CustomResourceWrapper get(Long id);

    FileDto getFormInfo(Long id);

    boolean delete(Long id);
}
