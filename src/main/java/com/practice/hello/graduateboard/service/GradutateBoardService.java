package com.practice.hello.graduateboard.service;



import com.practice.hello.graduateboard.dto.GradutateBoardCreateDTO;
import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.repository.GradutateBoardRepository;
import com.practice.hello.graduateboard.repository.GradutateCommentRepository;
import com.practice.hello.graduateboard.repository.GradutateReplyRepository;
import com.practice.hello.secretboard.dto.SecretBoardCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.repository.SecretBoardRepository;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import com.practice.hello.secretboard.repository.SecretReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class GradutateBoardService {


    private final GradutateBoardRepository gradutateBoardRepository;
    private final GradutateCommentRepository gradutateCommentRepository;


    private final GradutateReplyRepository gradutateReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<GraduateComment> graduateComments = gradutateCommentRepository.findByBoardId(id);
        for (GraduateComment graduateComment : graduateComments ) {
            gradutateReplyRepository.deleteAllByGraduateCommentId(graduateComment.getId());
        }

        // Now delete all comments associated with the board
        gradutateCommentRepository.deleteAll(graduateComments);

        // Finally, delete the board
       gradutateBoardRepository.deleteById(id);
        gradutateBoardRepository.flush();

    }
    public GraduateBoard saveBoard(GradutateBoardCreateDTO dto) {


        GraduateBoard graduateBoard = dto.toEntity();


        gradutateBoardRepository.save(graduateBoard);

        return graduateBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public GraduateBoard saveBoard(GraduateBoard graduateBoard) {
        return gradutateBoardRepository.save(graduateBoard);
    }


    public Optional<GraduateBoard> getBoardById(Long id){
        return gradutateBoardRepository.findById(id);
    }

    public List<GraduateBoard> readBoardAll(){

        return gradutateBoardRepository.findAll();
    }

    public void deleteBoard(Long id){

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
    public GraduateBoard updateBoard(Long id, GradutateBoardCreateDTO dto) {
        GraduateBoard graduateBoard = gradutateBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        graduateBoard.update(dto.title(), dto.content(), dto.author());
        return gradutateBoardRepository.save(graduateBoard);
    }

}
