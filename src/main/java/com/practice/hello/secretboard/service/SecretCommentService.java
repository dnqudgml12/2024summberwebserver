package com.practice.hello.secretboard.service;

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
public class SecretCommentService {
    private final SecretCommentRepository secretCommentRepository;

    private final SecretBoardRepository secretBoardRepository;

    public SecretComment saveComment(SecretCommentCreateDTO dto, Long boardId) {
        Optional<SecretBoard> boardOptional = secretBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            SecretBoard secretBoard = boardOptional.get();
            long sequenceNumber = secretCommentRepository.countBySecretBoard(secretBoard) + 1;
            SecretComment secretComment = SecretComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .secretBoard(secretBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return secretCommentRepository.save(secretComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<SecretComment> getCommentById(Long id) {

        return secretCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<SecretComment> commentOptional = secretCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            SecretComment secretComment = commentOptional.get();
            if (secretComment.getSecretBoard().getId() == boardId) {
                secretCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
