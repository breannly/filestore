package com.filestore.filestore.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File {

    @Id
    private Long id;
    @Transient
    private User owner;
    private String fileName;
    private String filePath;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "filePath")
    private String maskFilePath() {
        return "*****";
    }
}
