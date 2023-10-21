package com.legends.taskFlow.model.entity;

import com.legends.taskFlow.model.enums.Priority;
import com.legends.taskFlow.model.enums.TaskType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long taskId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type_name")
    private TaskType taskType;

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

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id", referencedColumnName = "user_id")
    private User assignedTo;

    @Column(name = "parent_id")
    private Integer parentId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_name")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status taskStatus;

    @Column(name = "created_on")
    private LocalDateTime creationOn;

}