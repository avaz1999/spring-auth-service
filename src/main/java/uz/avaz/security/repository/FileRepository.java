package uz.avaz.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.avaz.security.entity.File;

public interface FileRepository extends JpaRepository<File,Long> {
}
