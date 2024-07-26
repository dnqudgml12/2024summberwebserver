package com.practice.hello.social.repository;

import com.practice.hello.social.entity.SocialBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SocialBoardRepository extends JpaRepository<SocialBoard,Long> {
    Optional<SocialBoard>findById(Long id);



}
