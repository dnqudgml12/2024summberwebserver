package com.practice.hello.advertise.controller;

import com.practice.hello.advertise.dto.AdvertiseReplyCreatedDTO;
import com.practice.hello.advertise.entity.AdvertiseReply;
import com.practice.hello.advertise.service.AdvertiseReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/advertiseboard")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertiseReplyController {

    private final AdvertiseReplyService advertiseReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<AdvertiseReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody AdvertiseReplyCreatedDTO dto) {
        try {
            AdvertiseReply savedAdvertiseReply = advertiseReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertiseReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            advertiseReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
