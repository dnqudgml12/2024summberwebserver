package com.practice.hello.circle.controller;

import com.practice.hello.circle.dto.CircleReplyCreatedDTO;
import com.practice.hello.circle.entity.CircleReply;
import com.practice.hello.circle.service.CircleReplyService;
import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.service.FreeReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/circleboard")
@CrossOrigin(origins = "http://localhost:5173")
public class CircleReplyController {

    private final CircleReplyService circleReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<CircleReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CircleReplyCreatedDTO dto) {
        try {
            CircleReply savedCircleReply = circleReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCircleReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            circleReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
