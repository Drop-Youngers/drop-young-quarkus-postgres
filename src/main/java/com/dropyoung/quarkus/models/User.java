package com.dropyoung.quarkus.models;


import com.dropyoung.quarkus.enums.EGender;
import com.dropyoung.quarkus.enums.EPasswordResetStatus;
import com.dropyoung.quarkus.enums.ERole;
import com.dropyoung.quarkus.enums.EVerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String names;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private Set<ERole> roles;

    @Column(unique = true)
    private String telephone;

    private String password;

    @Enumerated(EnumType.STRING)
    private EVerificationStatus verificationStatus = EVerificationStatus.UNVERIFIED;

    @Enumerated(EnumType.STRING)
    private EPasswordResetStatus passwordResetStatus = EPasswordResetStatus.NOT_REQUESTED;

    @JoinColumn(name = "profile_image_id")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private File profileImage;

    @Column(unique = true, nullable = true, updatable = true, name = "verification_code")
    private String verificationCode;

    @Column(unique = true, nullable = true, updatable = true, name = "password_reset_code")
    private String passwordResetCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private String updatedAt;

}