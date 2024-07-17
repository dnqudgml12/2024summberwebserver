package com.practice.hello.social.controller;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.service.FreeReplyService;
import com.practice.hello.social.dto.SocialReplyCreatedDTO;
import com.practice.hello.social.entity.SocialReply;
import com.practice.hello.social.service.SocialReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/socialboard")
@CrossOrigin(origins = "http://localhost:5173")
public class SocialReplyController {

    private final SocialReplyService socialReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<SocialReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody SocialReplyCreatedDTO dto) {
        try {
            SocialReply savedSocialReply = socialReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            socialReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
