package com.practice.hello.graduateboard.service;

import com.practice.hello.graduateboard.dto.GradutateCommentCreateDTO;
import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.repository.GradutateBoardRepository;
import com.practice.hello.graduateboard.repository.GradutateCommentRepository;
import com.practice.hello.secretboard.dto.SecretCommentCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.repository.SecretBoardRepository;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GradutateCommentService {
    private final GradutateBoardRepository gradutateBoardRepository;
    private final GradutateCommentRepository gradutateCommentRepository;

    public GraduateComment saveComment(GradutateCommentCreateDTO dto, Long boardId) {
        Optional<GraduateBoard> boardOptional = gradutateBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            GraduateBoard graduateBoard = boardOptional.get();
            long sequenceNumber = gradutateCommentRepository.countByGraduateBoard(graduateBoard) + 1;
            GraduateComment graduateComment = GraduateComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .graduateBoard(graduateBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return gradutateCommentRepository.save(graduateComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<GraduateComment> getCommentById(Long id) {

        return gradutateCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<GraduateComment> commentOptional = gradutateCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            GraduateComment graduateComment = commentOptional.get();
            if (graduateComment.getGraduateBoard().getId() == boardId) {
                gradutateCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
