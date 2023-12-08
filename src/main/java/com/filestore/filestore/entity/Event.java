package com.filestore.filestore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    private Long id;
    private Long userId;
    private Long fileId;

    @Transient
    private User user;
    @Transient
    private File file;

    private Action action;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
