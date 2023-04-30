package uz.avaz.security.dto;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class CustomResourceWrapper {
    private String originalName;
    private String type;

    private final Resource resource;

    public CustomResourceWrapper(Resource resource) {
        this.resource = resource;
    }

    public CustomResourceWrapper(String originalName, Resource resource) {
        this.originalName = originalName;
        this.resource = resource;
    }

    public String getOriginalName() {
        if (originalName != null) {
            return originalName;
        }

        return resource.getFilename();
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Resource getResource() {
        return resource;
    }

    public File getFile() throws IOException {
        return resource.getFile();
    }

    public boolean exists() {
        return resource.exists();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
