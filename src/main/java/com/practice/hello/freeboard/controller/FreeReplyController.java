package com.practice.hello.freeboard.controller;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.service.FreeReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/freeboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class FreeReplyController {

    private final FreeReplyService freeReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<FreeReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody FreeReplyCreatedDTO dto) {
        try {
            FreeReply savedFreeReply = freeReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            freeReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
