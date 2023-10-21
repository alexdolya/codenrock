package com.legends.taskFlow.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "location")
    private List<User> users;
}
