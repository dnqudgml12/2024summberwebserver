package com.practice.hello.graduateboard.service;

import com.practice.hello.graduateboard.dto.GradutateReplyCreatedDTO;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.entity.GraduateReply;
import com.practice.hello.graduateboard.repository.GradutateCommentRepository;
import com.practice.hello.graduateboard.repository.GradutateReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class GradutateReplyService {
    private final GradutateCommentRepository gradutateCommentRepository;


    private final GradutateReplyRepository gradutateReplyRepository;

    public GraduateReply saveReply(GradutateReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<GraduateComment> commentOptional = gradutateCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            GraduateComment graduateComment = commentOptional.get();
            if (graduateComment.getGraduateBoard().getId() == boardId) {
                long sequenceNumber = gradutateReplyRepository.countByGraduateComment(graduateComment) + 1; // Calculate the sequence number
                GraduateReply graduateReply = dto.toEntity(graduateComment, sequenceNumber);
                return gradutateReplyRepository.save(graduateReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<GraduateReply> getReplyById(Long id) {
        return gradutateReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<GraduateReply> replyOptional = gradutateReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            GraduateReply graduateReply = replyOptional.get();
            if (graduateReply.getGraduateComment().getId() == commentId && graduateReply.getGraduateComment().getGraduateBoard().getId() == boardId) {
                gradutateReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
