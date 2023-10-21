package com.legends.taskFlow.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "statuses")
public class Status {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "status_id")
    private List<Project> projects;

    @OneToMany
    @JoinColumn(name = "issue_id")
    private List<Task> tasks;
}
