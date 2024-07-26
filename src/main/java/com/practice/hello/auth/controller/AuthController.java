package com.practice.hello.auth.controller;

import com.practice.hello.auth.dto.AuthDTO;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/api/userinfo")
    public ResponseEntity<AuthDTO> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Member> member = memberService.findByEmail(principal.getAttribute("email"));

        AuthDTO authDTO = AuthDTO.builder()
                .memberId(member.get().getId())
                .name(principal.getAttribute("name"))
                .email(principal.getAttribute("email"))
                .build();

        return ResponseEntity.ok(authDTO);
    }
}
