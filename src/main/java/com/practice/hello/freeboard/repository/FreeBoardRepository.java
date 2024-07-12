package com.practice.hello.freeboard.repository;

import com.practice.hello.freeboard.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
    Optional<FreeBoard>findById(Long id);



}
