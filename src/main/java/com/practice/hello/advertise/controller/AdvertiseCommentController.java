package com.practice.hello.advertise.controller;

import com.practice.hello.advertise.dto.AdvertiseCommentCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.service.AdvertiseCommentService;
import com.practice.hello.freeboard.dto.FreeCommentCreateDTO;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.service.FreeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/advertiseboard")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertiseCommentController {

    private final AdvertiseCommentService advertiseCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<AdvertiseComment> addComment(@PathVariable Long boardId, @RequestBody AdvertiseCommentCreateDTO dto) {
        try {
            AdvertiseComment savedAdvertiseComment = advertiseCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertiseComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            advertiseCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
