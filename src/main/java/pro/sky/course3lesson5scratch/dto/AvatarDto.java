package pro.sky.course3lesson5scratch.dto;

import pro.sky.course3lesson5scratch.model.Avatar;

import java.util.Objects;

public class AvatarDto {
    private Long id;
    private Long studentId;
    private String studentName;

    public AvatarDto() {
    }

    public AvatarDto(Long id, Long studentId, String studentName) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public static AvatarDto fromEntity(Avatar entity) {
        AvatarDto dto = new AvatarDto();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setStudentName(entity.getStudent().getName());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDto avatarDto = (AvatarDto) o;
        return Objects.equals(id, avatarDto.id) && Objects.equals(studentId, avatarDto.studentId) && Objects.equals(studentName, avatarDto.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, studentName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}

