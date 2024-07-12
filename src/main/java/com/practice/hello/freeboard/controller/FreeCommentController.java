package com.practice.hello.freeboard.controller;

import com.practice.hello.freeboard.dto.FreeCommentCreateDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.service.FreeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/freeboard")
@CrossOrigin(origins = "http://localhost:5173")
public class FreeCommentController {

    private final FreeCommentService freeCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<FreeComment> addComment(@PathVariable Long boardId, @RequestBody FreeCommentCreateDTO dto) {
        try {
            FreeComment savedFreeComment = freeCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            freeCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
