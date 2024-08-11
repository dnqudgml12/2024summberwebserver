package com.practice.hello.advertise.dto;


import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.image.entity.Image;
import com.practice.hello.member.entity.Member;

import java.util.ArrayList;
import java.util.List;


public record AdvertiseBoardCreateDTO(String title, String author, String content, int likes, List<AdvertiseComment> advertiseComments){

    public AdvertiseBoard toEntity(Member member, Image image) {
        return AdvertiseBoard.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .advertiseComment(new ArrayList<>()) // Initialize with an empty list of comments
                .member(member)
                .image(image)
                .build();
    }


}
