package com.dropyoung.quarkus.models;


import com.dropyoung.quarkus.enums.EFileSizeType;
import com.dropyoung.quarkus.enums.EFileStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class File extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Transient
    private String url;

    @Column(name = "size")
    private int size;

    @Column(name = "size_type")
    @Enumerated(EnumType.STRING)
    private EFileSizeType sizeType;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EFileStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    /*
    Used UUID because in the profileImage property of User
    will call file and file will still reference user, which will cause infinite nesting.
    Hence, causing serialization errors.
    */
    private String uploadedById;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private String updatedAt;
}