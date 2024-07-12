package com.practice.hello.service.board;

import com.practice.hello.dto.boardreq.ReplyCreatedDTO;
import com.practice.hello.entity.board.Comment;
import com.practice.hello.entity.board.Reply;
import com.practice.hello.repository.board.CommentRepository;
import com.practice.hello.repository.board.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    public Reply saveReply(ReplyCreatedDTO dto, Long commentId, Long boardId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (comment.getBoard().getId() == boardId) {
                long sequenceNumber = replyRepository.countByComment(comment) + 1; // Calculate the sequence number
                Reply reply = dto.toEntity(comment, sequenceNumber);
                return replyRepository.save(reply);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public Optional<Reply> getReplyById(Long id) {
        return replyRepository.findById(id);
    }

    public void deleteReply(Long replyId, Long commentId, Long boardId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if (replyOptional.isPresent()) {
            Reply reply = replyOptional.get();
            if (reply.getComment().getId() == commentId && reply.getComment().getBoard().getId() == boardId) {
                replyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Reply does not belong to the given comment or board");
            }
        } else {
            throw new RuntimeException("Reply not found");
        }
    }
}
