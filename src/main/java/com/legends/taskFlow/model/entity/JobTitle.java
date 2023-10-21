package com.legends.taskFlow.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "job_titles")
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job_title")
    private Integer jobTitleId;

    @Column(name = "name_job_title")
    private String nameJobTitle;

    @OneToMany(mappedBy = "jobTitle")
    private List<User> users;
}
