package com.intuit.sportseventsregistration.repository;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.dto.EventRegistration;
import com.intuit.sportseventsregistration.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    int countByUser(User user);
    List<EventRegistration> findAllByUser(User user);

    EventRegistration findByEventAndUser(Event event, User user);
}
