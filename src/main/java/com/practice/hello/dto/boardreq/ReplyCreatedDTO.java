package com.practice.hello.dto.boardreq;

import com.practice.hello.entity.board.Comment;
import com.practice.hello.entity.board.Reply;

public record ReplyCreatedDTO(String content, String author) {
    public Reply toEntity(Comment comment, long sequenceNumber) {
        return Reply.builder()
                .content(content)
                .author(author)
                .comment(comment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
