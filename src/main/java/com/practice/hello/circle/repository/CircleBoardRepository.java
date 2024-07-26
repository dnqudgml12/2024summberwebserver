package com.practice.hello.circle.repository;

import com.practice.hello.circle.entity.CircleBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CircleBoardRepository extends JpaRepository<CircleBoard,Long> {
    Optional<CircleBoard>findById(Long id);



}
