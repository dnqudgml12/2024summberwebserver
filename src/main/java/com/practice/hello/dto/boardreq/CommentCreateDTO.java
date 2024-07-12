package com.practice.hello.dto.boardreq;

import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;
import com.practice.hello.entity.board.Reply;

import java.util.ArrayList;
import java.util.List;
public record CommentCreateDTO(String content, String author) {
    public Comment toEntity(Board board) {
        return Comment.builder()
                .content(content)
                .author(author)
                .board(board)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
