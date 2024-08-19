package com.practice.hello.secretboard.service;



import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import com.practice.hello.secretboard.dto.SecretBoardCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.repository.SecretBoardRepository;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import com.practice.hello.secretboard.repository.SecretReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class SecretBoardService {

    private final MemberRepository memberRepository;
    private final SecretBoardRepository secretBoardRepository;
    private final SecretCommentRepository secretCommentRepository;


    private final SecretReplyRepository secretReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        SecretBoard secretBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!secretBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<SecretComment> secretComments = secretCommentRepository.findByBoardId(id);
        for (SecretComment secretComment : secretComments ) {
            secretReplyRepository.deleteAllBySecretCommentId(secretComment.getId());
        }

        // Now delete all comments associated with the board
        secretCommentRepository.deleteAll(secretComments);

        // Finally, delete the board
        secretBoardRepository.deleteById(id);
        secretBoardRepository.flush();

    }
    public SecretBoard saveBoard(SecretBoardCreateDTO dto, String uId) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        SecretBoard secretBoard = dto.toEntity(member);


        secretBoardRepository.save(secretBoard);

        return secretBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public SecretBoard saveBoard(SecretBoard secretBoard) {
        return secretBoardRepository.save(secretBoard);
    }


    public Optional<SecretBoard> getBoardById(Long id){
        return secretBoardRepository.findById(id);
    }

    public List<SecretBoard> readBoardAll(){
        return secretBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        secretBoardRepository.deleteById(id);
    }


    public SecretBoard likeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(true); // Set like status to true
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard unlikeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(false); // Set like status to false
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard updateBoard(Long id, SecretBoardCreateDTO dto,String uId) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        SecretBoard secretBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        if (!secretBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }
        secretBoard.update(dto.title(), dto.content());
        return secretBoardRepository.save(secretBoard);
    }

    public Page<SecretBoard> readBoardAll(Pageable pageable) {
        return secretBoardRepository.findAll(pageable);
    }

}
