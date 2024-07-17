package com.practice.hello.freshman.controller;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.service.FreeReplyService;
import com.practice.hello.freshman.dto.FreshmanReplyCreatedDTO;
import com.practice.hello.freshman.entity.FreshmanReply;
import com.practice.hello.freshman.service.FreshmanReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/freshmanboard")
@CrossOrigin(origins = "http://localhost:5173")
public class FreshmanReplyController {

    private final FreshmanReplyService freshmanReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<FreshmanReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody FreshmanReplyCreatedDTO dto) {
        try {
            FreshmanReply savedFreshmanReply = freshmanReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFreshmanReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            freshmanReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
