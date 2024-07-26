package com.practice.hello.freshman.service;

import com.practice.hello.freshman.dto.FreshmanReplyCreatedDTO;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.entity.FreshmanReply;
import com.practice.hello.freshman.repository.FreshmanCommentRepository;
import com.practice.hello.freshman.repository.FreshmanReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FreshmanReplyService {
    private final FreshmanReplyRepository freshmanReplyRepository;
    private final FreshmanCommentRepository freshmanCommentRepository;

    public FreshmanReply saveReply(FreshmanReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<FreshmanComment> commentOptional = freshmanCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            FreshmanComment freshmanComment = commentOptional.get();
            if (freshmanComment.getFreshman().getId() == boardId) {
                long sequenceNumber = freshmanReplyRepository.countByFreshmanComment(freshmanComment) + 1; // Calculate the sequence number
                FreshmanReply freshmanReply = dto.toEntity(freshmanComment, sequenceNumber);
                return freshmanReplyRepository.save(freshmanReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<FreshmanReply> getReplyById(Long id) {
        return freshmanReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<FreshmanReply> replyOptional = freshmanReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            FreshmanReply freshmanReply = replyOptional.get();
            if (freshmanReply.getFreshmanComment().getId() == commentId && freshmanReply.getFreshmanComment().getFreshman().getId() == boardId) {
                freshmanReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
