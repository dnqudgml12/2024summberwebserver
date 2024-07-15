package com.practice.hello.timetable.repository;

import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.timetable.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable,Long> {
    Optional<Timetable> findById(Long id);
}
