package uz.avaz.security.service.impl;

import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.dto.CustomResourceWrapper;
import uz.avaz.security.dto.FileDto;
import uz.avaz.security.service.FileService;

public class FileServiceImpl implements FileService {
    @Override
    public FileDto upload(MultipartFile file) {
        return null;
    }

    @Override
    public CustomResourceWrapper get(Long id) {
        return null;
    }

    @Override
    public FileDto getFormInfo(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
