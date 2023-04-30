package uz.avaz.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.ApiResponse;
import uz.avaz.security.dto.CustomResourceWrapper;
import uz.avaz.security.dto.FileDto;
import uz.avaz.security.entity.File;
import uz.avaz.security.entity.User;
import uz.avaz.security.payload.ResponseMessage;
import uz.avaz.security.repository.FileRepository;
import uz.avaz.security.repository.UserRepo;
import uz.avaz.security.service.FileService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public ApiResponse<?> upload(MultipartFile file, Long id) {
        try {
            File saveFile = FileDto.getEntity(file);
            Optional<User> byId = userRepo.findById(id);
            if (byId.isPresent()) {
                List<File> userPhoto = new ArrayList<>();
                byId.get().setUserPhotos(userPhoto);
                saveFile.setUser(userRepo.save(byId.get()));
                userPhoto.add(fileRepository.save(saveFile));
                fileWrite(file, getPath(FILE, saveFile));
                return new ApiResponse<>(true, ResponseMessage.SUCCESS,FileDto.getDto(saveFile));
            }else return new ApiResponse<>(false,ResponseMessage.USER_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void fileWrite(MultipartFile file, String path) {
        java.io.File privateFile = new java.io.File(path);
        try {
            var res = privateFile.getParentFile().mkdirs();
            res = privateFile.createNewFile();
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(Path.of(path)));
        } catch (IOException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
        }
    }

    private String getPath(String pathType, File saveFile) {
        return pathType + "/" + saveFile.getId();
    }

    @Override
    public CustomResourceWrapper get(Long id) {
        File file = findById(id);
        CustomResourceWrapper fileAsResource = getFileResource(getPath(FILE, file));
        fileAsResource.setOriginalName(file.getName());
        fileAsResource.setType(file.getType());
        return fileAsResource;
    }

    private CustomResourceWrapper getFileResource(String path) {
        java.io.File file = new java.io.File(path);
        CustomResourceWrapper customResourceWrapper;
        try {
            customResourceWrapper = new CustomResourceWrapper(new UrlResource(file.toPath().toUri()));
            if (customResourceWrapper.exists()) {
                return customResourceWrapper;
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (MalformedURLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            throw new RuntimeException(e);
        }
    }

    private File findById(Long id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public FileDto getFormInfo(Long id) {
        return FileDto.getDto(findById(id));
    }

    @Override
    public boolean delete(Long id) {
        try {
            File byId = findById(id);
            new java.io.File(getPath(FILE, byId)).delete();
            fileRepository.deleteById(id);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }
}
