package com.practice.hello.secretboard.controller;

import com.practice.hello.secretboard.dto.SecretReplyCreatedDTO;
import com.practice.hello.secretboard.entity.SecretReply;
import com.practice.hello.secretboard.service.SecretReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/secretboard")
@CrossOrigin(origins = "http://localhost:5173")
public class SecretReplyController {

    private final SecretReplyService secretReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<SecretReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody SecretReplyCreatedDTO dto) {
        try {
            SecretReply savedSecretReply = secretReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSecretReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            secretReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
