package com.legends.taskFlow.model.entity;

import com.legends.taskFlow.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "users")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "id_job_title")
    private JobTitle jobTitle;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expiration_date_token")
    private LocalDateTime expirationDateToken;

    @Column(name = "verify_code")
    private int verifyCode;

    @Column(name = "verify_status")
    private boolean verifyStatus;

    @OneToMany(mappedBy = "projectOwner")
    private List<Project> projects;

    @Enumerated(EnumType.STRING)
    @Column(name = "name_role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


}