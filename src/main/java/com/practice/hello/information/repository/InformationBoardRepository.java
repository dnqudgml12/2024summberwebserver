package com.practice.hello.information.repository;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.information.entity.InformationBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InformationBoardRepository extends JpaRepository<InformationBoard,Long> {
    Optional<InformationBoard>findById(Long id);



}
