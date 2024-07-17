package com.practice.hello.information.controller;

import com.practice.hello.freeboard.dto.FreeReplyCreatedDTO;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freeboard.service.FreeReplyService;
import com.practice.hello.information.dto.InformationReplyCreatedDTO;
import com.practice.hello.information.entity.InformationReply;
import com.practice.hello.information.service.InformationReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/informationboard")
@CrossOrigin(origins = "http://localhost:5173")
public class InformationReplyController {

    private final InformationReplyService informationReplyService;
    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<InformationReply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody InformationReplyCreatedDTO dto) {
        try {
            InformationReply savedInformationReply = informationReplyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInformationReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            informationReplyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
