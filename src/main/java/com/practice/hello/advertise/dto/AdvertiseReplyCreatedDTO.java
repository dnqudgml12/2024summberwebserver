package com.practice.hello.advertise.dto;

import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.entity.AdvertiseReply;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;

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
