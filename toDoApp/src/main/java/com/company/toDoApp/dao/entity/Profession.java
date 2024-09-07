package com.company.toDoApp.dao.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="profession")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "profession_name")
    private String professionName;
    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
