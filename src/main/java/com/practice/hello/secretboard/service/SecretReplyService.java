package com.practice.hello.secretboard.service;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import com.practice.hello.secretboard.dto.SecretReplyCreatedDTO;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.entity.SecretReply;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import com.practice.hello.secretboard.repository.SecretReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SecretReplyService {
    private final SecretReplyRepository secretReplyRepository;
    private final SecretCommentRepository secretCommentRepository;

    public SecretReply saveReply(SecretReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<SecretComment> commentOptional = secretCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            SecretComment secretComment = commentOptional.get();
            if (secretComment.getSecretBoard().getId() == boardId) {
                long sequenceNumber = secretReplyRepository.countBySecretComment(secretComment) + 1; // Calculate the sequence number
                SecretReply secretReply = dto.toEntity(secretComment, sequenceNumber);
                return secretReplyRepository.save(secretReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<SecretReply> getReplyById(Long id) {
        return secretReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<SecretReply> replyOptional = secretReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            SecretReply secretReply = replyOptional.get();
            if (secretReply.getSecretComment().getId() == commentId && secretReply.getSecretComment().getSecretBoard().getId() == boardId) {
                secretReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
