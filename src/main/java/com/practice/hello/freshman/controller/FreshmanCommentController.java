package com.practice.hello.freshman.controller;

import com.practice.hello.freshman.dto.FreshmanCommentCreateDTO;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.service.FreshmanCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/freshmanboard")
@CrossOrigin(origins = "http://localhost:5173")
public class FreshmanCommentController {

    private final FreshmanCommentService freshmanCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<FreshmanComment> addComment(@PathVariable Long boardId, @RequestBody FreshmanCommentCreateDTO dto) {
        try {
            FreshmanComment savedFreshmanComment = freshmanCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFreshmanComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            freshmanCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
