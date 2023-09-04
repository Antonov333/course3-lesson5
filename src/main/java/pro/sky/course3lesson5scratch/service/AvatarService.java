package pro.sky.course3lesson5scratch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.course3lesson5scratch.dto.AvatarDto;
import pro.sky.course3lesson5scratch.exception.AvatarNotExistedException;
import pro.sky.course3lesson5scratch.model.Avatar;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.repository.AvatarRepository;
import pro.sky.course3lesson5scratch.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findById(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public void getAvatarPictureFromDataBase(long id) {
        /*
        * @GetMapping(value = "/{id}/avatar-from-db")
public ResponseEntity‹byte[]› downloadAvatar(@PathVariable Long id) {
Avatar avatar = avatarService.findAvatar(id);
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
headers.setContentLength(avatar.getData().length);
return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
}
        * */
    }

    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findById(studentId).orElseThrow(AvatarNotExistedException::new);
    }

    public List<AvatarDto> findAll(int pageId) {
        PageRequest pageRequest = PageRequest.of(pageId, 2);
        List<Avatar> content = avatarRepository.findAll(pageRequest).getContent();
        return content.stream().map(AvatarDto::fromEntity).toList();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
