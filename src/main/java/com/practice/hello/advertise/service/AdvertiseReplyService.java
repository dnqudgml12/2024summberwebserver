package com.practice.hello.advertise.service;

import com.practice.hello.advertise.dto.AdvertiseReplyCreatedDTO;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.entity.AdvertiseReply;
import com.practice.hello.advertise.repository.AdvertiseCommentRepository;
import com.practice.hello.advertise.repository.AdvertiseReplyRepository;
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
public class AdvertiseReplyService {
    private final AdvertiseReplyRepository advertiseReplyRepository;
    private final AdvertiseCommentRepository advertiseCommentRepository;

    public AdvertiseReply saveReply(AdvertiseReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<AdvertiseComment> commentOptional = advertiseCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            AdvertiseComment advertiseComment = commentOptional.get();
            if (advertiseComment.getAdvertiseBoard().getId() == boardId) {
                long sequenceNumber = advertiseReplyRepository.countByAdvertiseComment(advertiseComment) + 1; // Calculate the sequence number
                AdvertiseReply advertiseReply = dto.toEntity(advertiseComment, sequenceNumber);
                return advertiseReplyRepository.save(advertiseReply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<AdvertiseReply> getReplyById(Long id) {
        return advertiseReplyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<AdvertiseReply> replyOptional = advertiseReplyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            AdvertiseReply advertiseReply = replyOptional.get();
            if (advertiseReply.getAdvertiseComment().getId() == commentId && advertiseReply.getAdvertiseComment().getAdvertiseBoard().getId() == boardId) {
                advertiseReplyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
