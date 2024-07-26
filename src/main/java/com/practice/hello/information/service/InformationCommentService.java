package com.practice.hello.information.service;

import com.practice.hello.information.dto.InformationCommentCreateDTO;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.repository.InformationBoardRepository;
import com.practice.hello.information.repository.InformationCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InformationCommentService {
    private final InformationCommentRepository informationCommentRepository;

    private final InformationBoardRepository informationBoardRepository;

    public InformationComment saveComment(InformationCommentCreateDTO dto, Long boardId) {
        Optional<InformationBoard> boardOptional = informationBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            InformationBoard informationBoard = boardOptional.get();
            long sequenceNumber = informationCommentRepository.countByInformationBoard(informationBoard) + 1;
            InformationComment informationComment = InformationComment.builder()
                    .content(dto.content())
                    .author(dto.author())
                    .informationBoard(informationBoard)
                    .sequenceNumber(sequenceNumber)
                    .build();
            return informationCommentRepository.save(informationComment);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
    public Optional<InformationComment> getCommentById(Long id) {

        return informationCommentRepository.findById(id);
    }

    public void deleteComment(Long commentId, Long boardId) {
        Optional<InformationComment> commentOptional = informationCommentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            InformationComment informationComment = commentOptional.get();
            if (informationComment.getInformationBoard().getId() == boardId) {
                informationCommentRepository.deleteById(commentId);
            } else {
                throw new RuntimeException("Comment does not belong to the given board");
            }
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
