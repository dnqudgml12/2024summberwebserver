package com.practice.hello.social.controller;

import com.practice.hello.social.dto.SocialCommentCreateDTO;
import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.service.SocialCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/socialboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class SocialCommentController {

    private final SocialCommentService socialCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<SocialComment> addComment(@PathVariable Long boardId, @RequestBody SocialCommentCreateDTO dto) {
        try {
            SocialComment savedSocialComment = socialCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            socialCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
