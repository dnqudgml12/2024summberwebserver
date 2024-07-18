package com.practice.hello.freeboard.service;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.repository.FreeBoardRepository;
import com.practice.hello.freeboard.repository.FreeCommentRepository;
import com.practice.hello.freeboard.repository.FreeReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class FreeBoardService {


    private final FreeBoardRepository freeBoardRepository;
    private final FreeCommentRepository freeCommentRepository;


    private final FreeReplyRepository freeReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<FreeComment> freeComments = freeCommentRepository.findByBoardId(id);
        for (FreeComment freeComment : freeComments) {
            freeReplyRepository.deleteAllByFreeCommentId(freeComment.getId());
        }

        // Now delete all comments associated with the board
        freeCommentRepository.deleteAll(freeComments);

        // Finally, delete the board
        freeBoardRepository.deleteById(id);
        freeBoardRepository.flush();

    }
    public FreeBoard saveBoard(FreeBoardCreateDTO dto) {


        FreeBoard freeBoard = dto.toEntity();


        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public FreeBoard saveBoard(FreeBoard freeBoard) {
        return freeBoardRepository.save(freeBoard);
    }


    public Optional<FreeBoard> getBoardById(Long id){
        return freeBoardRepository.findById(id);
    }

    public List<FreeBoard> readBoardAll(){
        return freeBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        freeBoardRepository.deleteById(id);
    }


    public FreeBoard likeBoard(Long id) {
        FreeBoard freeBoard = freeBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(true); // Set like status to true
        return freeBoardRepository.save(freeBoard);
    }

    @Transactional
    public FreeBoard unlikeBoard(Long id) {
        FreeBoard freeBoard = freeBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(false); // Set like status to false
        return freeBoardRepository.save(freeBoard);
    }



    // 게시물을 업데이트 하는 것 (put-> 전체를 다 update하므로)
// findById를 통해 특정 id의 게시물을 찾고 해당 id를 통해 불러온 값이 비어 있지 않으면 그 id에 해당하는 값을 불러온다.
//해당 하는 값의 entity에 있는 update함수를 사용한 뒤 update된 값을 db에 저장
    @Transactional
    public FreeBoard updateBoard(Long id, FreeBoardCreateDTO dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        freeBoard.update(dto.title(), dto.content(), dto.author());
        return freeBoardRepository.save(freeBoard);
    }


    public Page<FreeBoard> readBoardAll(Pageable pageable) {
        return freeBoardRepository.findAll(pageable);
    }


}

