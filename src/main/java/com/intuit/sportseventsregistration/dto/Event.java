package com.intuit.sportseventsregistration.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @Column(columnDefinition = "INTEGER(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "event_name")
    String eventName;

    @Column(name = "event_category")
    String eventCategory;

    @Column(name = "start_time")
    ZonedDateTime startTime;

    @Column(name = "end_time")
    ZonedDateTime endTime;

    @Column(name = "max_registration_limit")
    int maxRegistrationLimit;

    @Column(name = "current_registration_count")
    int currentRegistrationCount;
}
