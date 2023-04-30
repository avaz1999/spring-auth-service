package uz.avaz.security.dto;

import org.springframework.web.multipart.MultipartFile;
import uz.avaz.security.entity.File;

public class FileDto {
    private Long id;
    private String name;
    private String type;
    private Long size;

    public FileDto(Long id, String name, String type, Long size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public FileDto() {
    }

    public static File getEntity(MultipartFile file) {
        return new File(file.getOriginalFilename(), file.getContentType(), file.getSize());
    }

    public static FileDto getDto(File saveFile) {
        return new FileDto(saveFile.getId(), saveFile.getName(), saveFile.getType(), saveFile.getSize());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
