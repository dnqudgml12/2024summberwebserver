package com.practice.hello.circle.controller;

import com.practice.hello.circle.dto.CircleCommentCreateDTO;
import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.service.CircleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/circleboard")
@CrossOrigin(origins = "http://localhost:5173")
public class CircleCommentController {

    private final CircleCommentService circleCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CircleComment> addComment(@PathVariable Long boardId, @RequestBody CircleCommentCreateDTO dto) {
        try {
            CircleComment savedCircleComment = circleCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCircleComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            circleCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
