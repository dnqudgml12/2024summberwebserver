package com.practice.hello.graduateboard.dto;


import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;

import java.util.ArrayList;
import java.util.List;


public record GradutateBoardCreateDTO(String title, String author, String content, int likes, List<GraduateComment> graduateComments){

    public GraduateBoard toEntity() {
        return  GraduateBoard.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .graduateComment(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
