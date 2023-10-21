package com.legends.taskFlow.model.entity;

import com.legends.taskFlow.model.enums.ProjectType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "goal")
    private String goal;

    @Column(name = "start_plan_date")
    private LocalDateTime startPlanDate;

    @Column(name = "start_on")
    private LocalDateTime startOn;

    @Column(name = "end_plan_date")
    private LocalDateTime endPlanDate;

    @Column(name = "closed_on")
    private LocalDateTime closedOn;

    @Column(name = "estimate_time_plan")
    private String estimateTimePlan;

    @Column(name = "estimate_time_fact")
    private String estimateTimeFact;

    @Column(name = "project_description")
    private String projectDescription;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status projectStatus;

    @ManyToOne
    @JoinColumn (name = "owner_id")
    private User projectOwner;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type_name")
    private ProjectType projectTypeName;

    @Column(name = "created_on")
    private LocalDateTime creationOn;

    @OneToMany(mappedBy = "project")
    private List<Comment> comments;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
