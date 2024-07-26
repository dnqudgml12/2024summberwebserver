package com.practice.hello.circle.service;


import com.practice.hello.circle.dto.CircleBoardCreateDTO;
import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.repository.CircleBoardRepository;
import com.practice.hello.circle.repository.CircleCommentRepository;
import com.practice.hello.circle.repository.CircleReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class CircleBoardService {


    private final CircleBoardRepository circleBoardRepository;
    private final CircleCommentRepository circleCommentRepository;


    private final CircleReplyRepository circleReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<CircleComment> circleComments = circleCommentRepository.findByBoardId(id);
        for (CircleComment circleComment : circleComments) {
            circleReplyRepository.deleteAllByCircleCommentId(circleComment.getId());
        }

        // Now delete all comments associated with the board
        circleCommentRepository.deleteAll(circleComments);

        // Finally, delete the board
        circleBoardRepository.deleteById(id);
        circleBoardRepository.flush();

    }

    /*
    public CircleBoard saveBoard(CircleBoardCreateDTO dto) {


        CircleBoard circleBoard = dto.toEntity();


        circleBoardRepository.save(circleBoard);

        return circleBoard;
    }

     */
    public CircleBoard saveBoard(CircleBoardCreateDTO dto, String imageUrl) {
        CircleBoard circleBoard = dto.toEntity(imageUrl);
        return circleBoardRepository.save(circleBoard);
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public CircleBoard saveBoard(CircleBoard circleBoard) {
        return circleBoardRepository.save(circleBoard);
    }


    public Optional<CircleBoard> getBoardById(Long id){
        return circleBoardRepository.findById(id);
    }

    public List<CircleBoard> readBoardAll(){
        return circleBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        circleBoardRepository.deleteById(id);
    }


    public CircleBoard likeBoard(Long id) {
        CircleBoard circleBoard = circleBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        circleBoard.setLikeStatus(true); // Set like status to true
        return circleBoardRepository.save(circleBoard);
    }

    @Transactional
    public CircleBoard unlikeBoard(Long id) {
        CircleBoard circleBoard = circleBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        circleBoard.setLikeStatus(false); // Set like status to false
        return circleBoardRepository.save(circleBoard);
    }

/*
    @Transactional
    public CircleBoard updateBoard(Long id, CircleBoardCreateDTO dto) {
        CircleBoard circleBoard = circleBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        circleBoard.update(dto.title(), dto.content(), dto.author());
        return circleBoardRepository.save(circleBoard);
    }
*/
@Transactional
public CircleBoard updateBoard(Long id, CircleBoardCreateDTO dto, String imageUrl) {
    CircleBoard circleBoard = circleBoardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Board not found"));
    circleBoard.update(dto.title(), dto.content(), dto.author());
    if (!imageUrl.isEmpty()) {
        circleBoard.setImagePath(imageUrl);
    }
    return circleBoardRepository.save(circleBoard);
}
    public Page<CircleBoard> readBoardAll(Pageable pageable) {
        return circleBoardRepository.findAll(pageable);
    }

}
