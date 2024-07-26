package com.practice.hello.circle.dto;


import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public record CircleBoardCreateDTO(String title, String author, String content, int likes, List<CircleComment> circleComments, MultipartFile imageFile){

    public CircleBoard toEntity(String imageUrl) {
        return CircleBoard.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .circleComment(new ArrayList<>()) // Initialize with an empty list of comments
                .imagePath(imageUrl)
                .build();
    }


}
