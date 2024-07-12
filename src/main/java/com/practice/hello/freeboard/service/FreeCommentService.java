package com.practice.hello.freeboard.service;

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
public class FreeCommentService {
    private final FreeCommentRepository freeCommentRepository;

    private final FreeBoardRepository freeBoardRepository;

    public FreeComment saveComment(FreeCommentCreateDTO dto, Long boardId) {
        Optional<FreeBoard> boardOptional = freeBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            FreeBoard freeBoard = boardOptional.get();
            long sequenceNumber = freeCommentRepository.countByFreeBoard(freeBoard) + 1;
            FreeComment freeComment = FreeComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .freeBoard(freeBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return freeCommentRepository.save(freeComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<FreeComment> getCommentById(Long id) {

        return freeCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<FreeComment> commentOptional = freeCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            FreeComment freeComment = commentOptional.get();
            if (freeComment.getFreeBoard().getId() == boardId) {
                freeCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
