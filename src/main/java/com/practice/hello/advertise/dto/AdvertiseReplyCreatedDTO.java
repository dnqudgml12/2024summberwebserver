package com.practice.hello.advertise.dto;

import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.entity.AdvertiseReply;

public record AdvertiseReplyCreatedDTO(String content, String author) {
    public AdvertiseReply toEntity(AdvertiseComment advertiseComment, long sequenceNumber) {
        return AdvertiseReply.builder()
                .content(content)
                .author(author)
                .advertiseComment(advertiseComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
