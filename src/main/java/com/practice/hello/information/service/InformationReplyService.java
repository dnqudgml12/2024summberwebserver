package com.practice.hello.information.service;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import com.practice.hello.information.dto.InformationReplyCreatedDTO;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.entity.InformationReply;
import com.practice.hello.information.repository.InformationCommentRepository;
import com.practice.hello.information.repository.InformationReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class InformationReplyService {
    private final InformationReplyRepository informationReplyRepository;
    private final InformationCommentRepository informationCommentRepository;

    public InformationReply saveReply(InformationReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<InformationComment> commentOptional = informationCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            InformationComment informationComment = commentOptional.get();
            if (informationComment.getInformationBoard().getId() == boardId) {
                long sequenceNumber = informationReplyRepository.countByInformationComment(informationComment) + 1; // Calculate the sequence number
                InformationReply informationReply = dto.toEntity(informationComment, sequenceNumber);
                return informationReplyRepository.save(informationReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<InformationReply> getReplyById(Long id) {
        return informationReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<InformationReply> replyOptional = informationReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            InformationReply informationReply = replyOptional.get();
            if (informationReply.getInformationComment().getId() == commentId && informationReply.getInformationComment().getInformationBoard().getId() == boardId) {
                informationReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
