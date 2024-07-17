package com.practice.hello.advertise.service;


import com.practice.hello.advertise.dto.AdvertiseBoardCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.repository.AdvertiseBoardRepository;
import com.practice.hello.advertise.repository.AdvertiseCommentRepository;
import com.practice.hello.advertise.repository.AdvertiseReplyRepository;
import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class AdvertiseBoardService {


    private final AdvertiseBoardRepository advertiseBoardRepository;
    private final AdvertiseCommentRepository advertiseCommentRepository;


    private final AdvertiseReplyRepository advertiseReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<AdvertiseComment> advertiseComments = advertiseCommentRepository.findByBoardId(id);
        for (AdvertiseComment advertiseComment : advertiseComments) {
            advertiseReplyRepository.deleteAllByAdvertiseCommentId(advertiseComment.getId());
        }

        // Now delete all comments associated with the board
        advertiseCommentRepository.deleteAll(advertiseComments);

        // Finally, delete the board
        advertiseBoardRepository.deleteById(id);
        advertiseBoardRepository.flush();

    }
    public AdvertiseBoard saveBoard(AdvertiseBoardCreateDTO dto) {


        AdvertiseBoard advertiseBoard = dto.toEntity();


        advertiseBoardRepository.save(advertiseBoard);


        return advertiseBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public AdvertiseBoard saveBoard(AdvertiseBoard advertiseBoard) {
        return advertiseBoardRepository.save(advertiseBoard);
    }


    public Optional<AdvertiseBoard> getBoardById(Long id){
        return advertiseBoardRepository.findById(id);
    }

    public List<AdvertiseBoard> readBoardAll(){
        return advertiseBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        advertiseBoardRepository.deleteById(id);
    }


    public AdvertiseBoard likeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(true); // Set like status to true
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard unlikeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(false); // Set like status to false
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard updateBoard(Long id, AdvertiseBoardCreateDTO dto) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        advertiseBoard.update(dto.title(), dto.content(), dto.author());
        return advertiseBoardRepository.save(advertiseBoard);
    }

}
