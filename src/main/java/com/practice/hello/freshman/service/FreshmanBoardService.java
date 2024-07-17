package com.practice.hello.freshman.service;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import com.practice.hello.freshman.dto.FreshmanBoardCreateDTO;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.repository.FreshmanBoardRepository;
import com.practice.hello.freshman.repository.FreshmanCommentRepository;
import com.practice.hello.freshman.repository.FreshmanReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class FreshmanBoardService {


    private final FreshmanBoardRepository freshmanRepository;
    private final FreshmanCommentRepository freshmanCommentRepository;


    private final FreshmanReplyRepository freshmanReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<FreshmanComment> freshmanComments = freshmanCommentRepository.findByBoardId(id);
        for (FreshmanComment freshmanComment : freshmanComments) {
            freshmanReplyRepository.deleteAllByFreshmanCommentId(freshmanComment.getId());
        }

        // Now delete all comments associated with the board
        freshmanCommentRepository.deleteAll(freshmanComments);

        // Finally, delete the board
        freshmanRepository.flush();

    }
    public Freshman saveBoard(FreshmanBoardCreateDTO dto) {


        Freshman freshman = dto.toEntity();


        freshmanRepository.save(freshman);

        return freshman;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public Freshman saveBoard(Freshman freshman) {
        return freshmanRepository.save(freshman);
    }


    public Optional<Freshman> getBoardById(Long id){
        return freshmanRepository.findById(id);
    }

    public List<Freshman> readBoardAll(){
        return freshmanRepository.findAll();
    }

    public void deleteBoard(Long id){
        freshmanRepository.deleteById(id);
    }


    public Freshman likeBoard(Long id) {
        Freshman freshman = freshmanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freshman.setLikeStatus(true); // Set like status to true
        return freshmanRepository.save(freshman);
    }

    @Transactional
    public Freshman unlikeBoard(Long id) {
        Freshman freshman = freshmanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freshman.setLikeStatus(false); // Set like status to false
        return freshmanRepository.save(freshman);
    }

    @Transactional
    public Freshman updateBoard(Long id, FreshmanBoardCreateDTO dto) {
        Freshman freshman = freshmanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        freshman.update(dto.title(), dto.content(), dto.author());
        return freshmanRepository.save(freshman);
    }

}
