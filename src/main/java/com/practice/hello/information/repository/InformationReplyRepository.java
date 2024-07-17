package com.practice.hello.information.repository;



import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.entity.InformationReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InformationReplyRepository extends JpaRepository<InformationReply,Long> {
    Optional<InformationReply> findById(Long id);

    Long countByInformationComment(InformationComment informationComment);


    @Modifying
    @Transactional
    void deleteAllByInformationCommentId(Long commentId); // Add this method
}
