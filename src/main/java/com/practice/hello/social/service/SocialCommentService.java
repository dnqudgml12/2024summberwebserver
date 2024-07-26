package com.practice.hello.social.service;

import com.practice.hello.social.dto.SocialCommentCreateDTO;
import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.repository.SocialBoardRepository;
import com.practice.hello.social.repository.SocialCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SocialCommentService {
    private final SocialCommentRepository socialCommentRepository;

    private final SocialBoardRepository socialBoardRepository;

    public SocialComment saveComment(SocialCommentCreateDTO dto, Long boardId) {
        Optional<SocialBoard> boardOptional = socialBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            SocialBoard socialBoard = boardOptional.get();
            long sequenceNumber = socialCommentRepository.countBySocialBoard(socialBoard) + 1;
            SocialComment socialComment = SocialComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .socialBoard(socialBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return socialCommentRepository.save(socialComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<SocialComment> getCommentById(Long id) {

        return socialCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<SocialComment> commentOptional = socialCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            SocialComment socialComment = commentOptional.get();
            if (socialComment.getSocialBoard().getId() == boardId) {
                socialCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
