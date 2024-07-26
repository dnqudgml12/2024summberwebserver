package com.practice.hello.advertise.repository;

import com.practice.hello.advertise.entity.AdvertiseBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdvertiseBoardRepository extends JpaRepository<AdvertiseBoard,Long> {
    Optional<AdvertiseBoard>findById(Long id);



}
