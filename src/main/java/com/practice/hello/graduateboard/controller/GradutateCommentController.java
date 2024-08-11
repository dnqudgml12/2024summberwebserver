package com.practice.hello.graduateboard.controller;

import com.practice.hello.graduateboard.dto.GradutateCommentCreateDTO;
import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.service.GradutateCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/graduateboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class GradutateCommentController {

    private final GradutateCommentService gradutateCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<GraduateComment> addComment(@PathVariable Long boardId, @RequestBody GradutateCommentCreateDTO dto) {
        try {
            GraduateComment savedGraduateComment = gradutateCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGraduateComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            gradutateCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
