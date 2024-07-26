package com.practice.hello.advertise.service;

import com.practice.hello.advertise.dto.AdvertiseCommentCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.repository.AdvertiseBoardRepository;
import com.practice.hello.advertise.repository.AdvertiseCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdvertiseCommentService {
    private final AdvertiseCommentRepository advertiseCommentRepository;

    private final AdvertiseBoardRepository advertiseBoardRepository;

    public AdvertiseComment saveComment(AdvertiseCommentCreateDTO dto, Long boardId) {
        Optional<AdvertiseBoard> boardOptional = advertiseBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            AdvertiseBoard advertiseBoard = boardOptional.get();
            long sequenceNumber = advertiseCommentRepository.countByAdvertiseBoard(advertiseBoard) + 1;
            AdvertiseComment advertiseComment = AdvertiseComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .advertiseBoard(advertiseBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return advertiseCommentRepository.save(advertiseComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<AdvertiseComment> getCommentById(Long id) {

        return advertiseCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<AdvertiseComment> commentOptional = advertiseCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            AdvertiseComment advertiseComment = commentOptional.get();
            if (advertiseComment.getAdvertiseBoard().getId() == boardId) {
                advertiseCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
