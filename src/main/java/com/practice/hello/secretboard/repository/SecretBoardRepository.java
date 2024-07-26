package com.practice.hello.secretboard.repository;

import com.practice.hello.secretboard.entity.SecretBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SecretBoardRepository extends JpaRepository<SecretBoard,Long> {
    Optional<SecretBoard>findById(Long id);



}
