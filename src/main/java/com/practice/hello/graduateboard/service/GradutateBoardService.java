package com.practice.hello.graduateboard.service;


import com.practice.hello.graduateboard.dto.GradutateBoardCreateDTO;
import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.repository.GradutateBoardRepository;
import com.practice.hello.graduateboard.repository.GradutateCommentRepository;
import com.practice.hello.graduateboard.repository.GradutateReplyRepository;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
@Slf4j
public class GradutateBoardService {


    private final GradutateBoardRepository gradutateBoardRepository;
    private final GradutateCommentRepository gradutateCommentRepository;

    private final MemberRepository memberRepository;
    private final GradutateReplyRepository gradutateReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {

        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        GraduateBoard graduateBoard = gradutateBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));

        if (!graduateBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<GraduateComment> graduateComments = gradutateCommentRepository.findByBoardId(id);
        for (GraduateComment graduateComment : graduateComments) {
            gradutateReplyRepository.deleteAllByGraduateCommentId(graduateComment.getId());
        }

        // Now delete all comments associated with the board
        gradutateCommentRepository.deleteAll(graduateComments);

        // Finally, delete the board
        gradutateBoardRepository.deleteById(id);
        gradutateBoardRepository.flush();

    }

    public GraduateBoard saveBoard(GradutateBoardCreateDTO dto,String uId) {

        log.info("Creating post for user: {}", uId);
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        GraduateBoard graduateBoard = dto.toEntity(member);


        gradutateBoardRepository.save(graduateBoard);

        return graduateBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public GraduateBoard saveBoard(GraduateBoard graduateBoard) {
        return gradutateBoardRepository.save(graduateBoard);
    }


    public Optional<GraduateBoard> getBoardById(Long id) {
 log.error("getBoardById 함수실행");
        return gradutateBoardRepository.findById(id);
    }

    public List<GraduateBoard> readBoardAll() {

        return gradutateBoardRepository.findAll();
    }

    public void deleteBoard(Long id) {

        gradutateBoardRepository.deleteById(id);
    }


    public GraduateBoard likeBoard(Long id) {
        GraduateBoard graduateBoard = gradutateBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        graduateBoard.setLikeStatus(true); // Set like status to true
        return gradutateBoardRepository.save(graduateBoard);
    }

    @Transactional
    public GraduateBoard unlikeBoard(Long id) {
        GraduateBoard graduateBoard = gradutateBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        graduateBoard.setLikeStatus(false); // Set like status to false
        return gradutateBoardRepository.save(graduateBoard);
    }

    @Transactional
    public GraduateBoard updateBoard(Long id, GradutateBoardCreateDTO dto,String uId) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        GraduateBoard graduateBoard = gradutateBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!graduateBoard .getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }
        graduateBoard.update(dto.title(), dto.content());
        return gradutateBoardRepository.save(graduateBoard);
    }

    public Page<GraduateBoard> readBoardAll(Pageable pageable) {
        return gradutateBoardRepository.findAll(pageable);
    }

}
