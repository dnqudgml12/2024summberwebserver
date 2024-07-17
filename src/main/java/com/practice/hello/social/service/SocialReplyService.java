package com.practice.hello.social.service;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import com.practice.hello.social.dto.SocialReplyCreatedDTO;
import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.entity.SocialReply;
import com.practice.hello.social.repository.SocialCommentRepository;
import com.practice.hello.social.repository.SocialReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SocialReplyService {
    private final SocialReplyRepository socialReplyRepository;
    private final SocialCommentRepository socialCommentRepository;

    public SocialReply saveReply(SocialReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<SocialComment> commentOptional = socialCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            SocialComment socialComment = commentOptional.get();
            if (socialComment.getSocialBoard().getId() == boardId) {
                long sequenceNumber = socialReplyRepository.countBySocialComment(socialComment) + 1; // Calculate the sequence number
                SocialReply socialReply = dto.toEntity(socialComment, sequenceNumber);
                return socialReplyRepository.save(socialReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<SocialReply> getReplyById(Long id) {
        return socialReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<SocialReply> replyOptional = socialReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            SocialReply socialReply = replyOptional.get();
            if (socialReply.getSocialComment().getId() == commentId && socialReply.getSocialComment().getSocialBoard().getId() == boardId) {
                socialReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
