package com.practice.hello.freeboard.repository;



import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FreeReplyRepository extends JpaRepository<FreeReply,Long> {
    Optional<FreeReply> findById(Long id);

    Long countByFreeComment(FreeComment freeComment);


    @Modifying
    @Transactional
    void deleteAllByFreeCommentId(Long commentId); // Add this method
    // Spring Data JPA가 메소드 이름을 기반으로 자동으로 쿼리생성 그래서 body가 필요 없다
}
