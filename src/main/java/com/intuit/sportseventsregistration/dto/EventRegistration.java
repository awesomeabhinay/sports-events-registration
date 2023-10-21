package com.intuit.sportseventsregistration.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_registration")
public class EventRegistration {

    @Id
    @Column(columnDefinition = "INTEGER(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    User user;
    @ManyToOne
    Event event;
}
