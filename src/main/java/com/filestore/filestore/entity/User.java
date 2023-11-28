package com.filestore.filestore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    @Transient
    @ToString.Exclude
    private List<File> files;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "*****";
    }
}
