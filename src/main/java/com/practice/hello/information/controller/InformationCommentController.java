package com.practice.hello.information.controller;

import com.practice.hello.information.dto.InformationCommentCreateDTO;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.service.InformationCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/informationboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class InformationCommentController {

    private final InformationCommentService informationCommentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<InformationComment> addComment(@PathVariable Long boardId, @RequestBody InformationCommentCreateDTO dto) {
        try {
            InformationComment savedInformationComment = informationCommentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInformationComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            informationCommentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
