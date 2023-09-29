package com.intuit.sportseventsregistration.repository;

import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    int countByUser(User user);
    List<EventRegistration> findAllByUser(User user);
}
