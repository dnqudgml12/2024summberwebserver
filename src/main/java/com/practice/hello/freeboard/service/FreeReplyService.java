package com.practice.hello.freeboard.service;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FreeReplyService {
    private final FreeReplyRepository freeReplyRepository;
    private final FreeCommentRepository freeCommentRepository;

    public FreeReply saveReply(FreeReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<FreeComment> commentOptional = freeCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            FreeComment freeComment = commentOptional.get();
            if (freeComment.getFreeBoard().getId() == boardId) {
                long sequenceNumber = freeReplyRepository.countByFreeComment(freeComment) + 1; // Calculate the sequence number
                FreeReply freeReply = dto.toEntity(freeComment, sequenceNumber);
                return freeReplyRepository.save(freeReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<FreeReply> getReplyById(Long id) {
        return freeReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<FreeReply> replyOptional = freeReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            FreeReply freeReply = replyOptional.get();
            if (freeReply.getFreeComment().getId() == commentId && freeReply.getFreeComment().getFreeBoard().getId() == boardId) {
                freeReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
