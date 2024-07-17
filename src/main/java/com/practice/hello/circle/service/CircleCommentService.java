package com.practice.hello.circle.service;

import com.practice.hello.circle.dto.CircleCommentCreateDTO;
import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.repository.CircleBoardRepository;
import com.practice.hello.circle.repository.CircleCommentRepository;
import com.practice.hello.freeboard.dto.FreeCommentCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CircleCommentService {
    private final CircleCommentRepository circleCommentRepository;

    private final CircleBoardRepository circleBoardRepository;

    public CircleComment saveComment(CircleCommentCreateDTO dto, Long boardId) {
        Optional<CircleBoard> boardOptional = circleBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            CircleBoard circleBoard = boardOptional.get();
            long sequenceNumber = circleCommentRepository.countByCircleBoard(circleBoard) + 1;
            CircleComment circleComment = CircleComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .circleBoard(circleBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return circleCommentRepository.save(circleComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<CircleComment> getCommentById(Long id) {

        return circleCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<CircleComment> commentOptional = circleCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CircleComment circleComment = commentOptional.get();
            if (circleComment.getCircleBoard().getId() == boardId) {
                circleCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
