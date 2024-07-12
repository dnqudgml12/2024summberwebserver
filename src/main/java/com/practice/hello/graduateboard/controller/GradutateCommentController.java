package com.practice.hello.graduateboard.controller;

import com.practice.hello.secretboard.dto.SecretCommentCreateDTO;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.service.SecretCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/graduateboard")
@CrossOrigin(origins = "http://localhost:5173")
public class GradutateCommentController {

    private final SecretCommentService secretCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<SecretComment> addComment(@PathVariable Long boardId, @RequestBody SecretCommentCreateDTO dto) {
        try {
            SecretComment savedSecretComment = secretCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSecretComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            secretCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
