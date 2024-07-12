package com.practice.hello.graduateboard.repository;

import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.secretboard.entity.SecretBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GradutateBoardRepository extends JpaRepository<GraduateBoard,Long> {
    Optional<GraduateBoard>findById(Long id);



}
