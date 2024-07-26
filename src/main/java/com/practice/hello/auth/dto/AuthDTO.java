package com.practice.hello.auth.dto;

import lombok.Builder;


@Builder
public record AuthDTO(
        Long memberId,
        String name,
        String email

) {
}