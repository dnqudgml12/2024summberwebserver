package com.practice.hello.circle.service;

import com.practice.hello.circle.dto.CircleReplyCreatedDTO;
import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.entity.CircleReply;
import com.practice.hello.circle.repository.CircleCommentRepository;
import com.practice.hello.circle.repository.CircleReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CircleReplyService {
    private final CircleReplyRepository circleReplyRepository;
    private final CircleCommentRepository circleCommentRepository;

    public CircleReply saveReply(CircleReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<CircleComment> commentOptional = circleCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CircleComment circleComment = commentOptional.get();
            if (circleComment.getCircleBoard().getId() == boardId) {
                long sequenceNumber = circleReplyRepository.countByCircleComment(circleComment) + 1; // Calculate the sequence number
                CircleReply circleReply = dto.toEntity(circleComment, sequenceNumber);
                return circleReplyRepository.save(circleReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<CircleReply> getReplyById(Long id) {
        return circleReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<CircleReply> replyOptional = circleReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            CircleReply circleReply = replyOptional.get();
            if (circleReply.getCircleComment().getId() == commentId && circleReply.getCircleComment().getCircleBoard().getId() == boardId) {
                circleReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
