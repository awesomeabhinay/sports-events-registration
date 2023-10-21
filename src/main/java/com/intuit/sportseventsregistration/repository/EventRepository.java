package com.intuit.sportseventsregistration.repository;

import com.intuit.sportseventsregistration.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
