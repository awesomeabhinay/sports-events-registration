package com.intuit.sportseventsregistration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(columnDefinition = "INTEGER(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String username;

    @Column
    String email;
}
