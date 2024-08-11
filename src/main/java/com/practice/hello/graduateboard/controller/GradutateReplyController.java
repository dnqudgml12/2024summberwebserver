package com.practice.hello.graduateboard.controller;

import com.practice.hello.graduateboard.dto.GradutateReplyCreatedDTO;
import com.practice.hello.graduateboard.entity.GraduateReply;
import com.practice.hello.graduateboard.service.GradutateReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/graduateboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class GradutateReplyController {

    private final GradutateReplyService gradutateReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<GraduateReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody GradutateReplyCreatedDTO dto) {
        try {
            GraduateReply saveGraduateReply = gradutateReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveGraduateReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            gradutateReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
