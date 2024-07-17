package com.practice.hello.freshman.service;

import com.practice.hello.freeboard.dto.FreeCommentCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freshman.dto.FreshmanCommentCreateDTO;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.repository.FreshmanBoardRepository;
import com.practice.hello.freshman.repository.FreshmanCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FreshmanCommentService {
    private final FreshmanCommentRepository freshmanCommentRepository;

    private final FreshmanBoardRepository freshmanBoardRepository;

    public FreshmanComment saveComment(FreshmanCommentCreateDTO dto, Long boardId) {
        Optional<Freshman> boardOptional = freshmanBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Freshman freshman = boardOptional.get();
            long sequenceNumber = freshmanCommentRepository.countByFreshman(freshman) + 1;
            FreshmanComment freshmanComment = FreshmanComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .freshman(freshman)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return freshmanCommentRepository.save(freshmanComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<FreshmanComment> getCommentById(Long id) {

        return freshmanCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<FreshmanComment> commentOptional = freshmanCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            FreshmanComment freshmanComment = commentOptional.get();
            if (freshmanComment.getFreshman().getId() == boardId) {
                freshmanCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
