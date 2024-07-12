package com.practice.hello.secretboard.dto;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;

import java.util.ArrayList;
import java.util.List;


public record SecretBoardCreateDTO(String title, String author, String content, int likes, List<SecretComment> secretComments){

    public SecretBoard toEntity() {
        return  SecretBoard.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .secretComment(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
