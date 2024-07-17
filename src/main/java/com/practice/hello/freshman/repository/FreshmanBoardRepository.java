package com.practice.hello.freshman.repository;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freshman.entity.Freshman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FreshmanBoardRepository extends JpaRepository<Freshman,Long> {
    Optional<Freshman>findById(Long id);



}
