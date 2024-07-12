package com.practice.hello.service.board;

import com.practice.hello.dto.boardreq.CommentCreateDTO;
import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;
import com.practice.hello.repository.board.BoardRepository;
import com.practice.hello.repository.board.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    public Comment saveComment(CommentCreateDTO dto, Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            long sequenceNumber = commentRepository.countByBoard(board) + 1;
            Comment comment = Comment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .board(board)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<Comment> getCommentById(Long id) {

        return commentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (comment.getBoard().getId() == boardId) {
                commentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
