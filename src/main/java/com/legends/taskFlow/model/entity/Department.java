package com.legends.taskFlow.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "name_department")
    private String nameDepartment;

    @OneToMany(mappedBy = "department")
    private List<User> users;
}
