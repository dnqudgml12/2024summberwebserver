package com.practice.hello.information.service;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import com.practice.hello.information.dto.InformationBoardCreateDTO;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.repository.InformationBoardRepository;
import com.practice.hello.information.repository.InformationCommentRepository;
import com.practice.hello.information.repository.InformationReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class InformationBoardService {


    private final InformationBoardRepository informationBoardRepository;
    private final InformationCommentRepository informationCommentRepository;


    private final InformationReplyRepository informationReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<InformationComment> informationComments = informationCommentRepository.findByBoardId(id);
        for (InformationComment informationComment : informationComments) {
            informationReplyRepository.deleteAllByInformationCommentId(informationComment.getId());
        }

        // Now delete all comments associated with the board
        informationCommentRepository.deleteAll(informationComments);

        // Finally, delete the board
        informationBoardRepository.deleteById(id);
        informationBoardRepository.flush();

    }
    public InformationBoard saveBoard(InformationBoardCreateDTO dto) {


        InformationBoard informationBoard = dto.toEntity();


        informationBoardRepository.save(informationBoard);

        return informationBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public InformationBoard saveBoard(InformationBoard informationBoard) {
        return informationBoardRepository.save(informationBoard);
    }


    public Optional<InformationBoard> getBoardById(Long id){
        return informationBoardRepository.findById(id);
    }

    public List<InformationBoard> readBoardAll(){
        return informationBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        informationBoardRepository.deleteById(id);
    }


    public InformationBoard likeBoard(Long id) {
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        informationBoard.setLikeStatus(true); // Set like status to true
        return informationBoardRepository.save(informationBoard);
    }

    @Transactional
    public InformationBoard unlikeBoard(Long id) {
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        informationBoard.setLikeStatus(false); // Set like status to false
        return informationBoardRepository.save(informationBoard);
    }

    @Transactional
    public InformationBoard updateBoard(Long id, InformationBoardCreateDTO dto) {
        InformationBoard informationBoard = informationBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        informationBoard.update(dto.title(), dto.content(), dto.author());
        return informationBoardRepository.save(informationBoard);
    }

}
