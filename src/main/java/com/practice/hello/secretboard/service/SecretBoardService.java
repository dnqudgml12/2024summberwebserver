package com.practice.hello.secretboard.service;



import com.practice.hello.secretboard.dto.SecretBoardCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.repository.SecretBoardRepository;
import com.practice.hello.secretboard.repository.SecretCommentRepository;
import com.practice.hello.secretboard.repository.SecretReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class SecretBoardService {


    private final SecretBoardRepository secretBoardRepository;
    private final SecretCommentRepository secretCommentRepository;


    private final SecretReplyRepository secretReplyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<SecretComment> secretComments = secretCommentRepository.findByBoardId(id);
        for (SecretComment secretComment : secretComments ) {
            secretReplyRepository.deleteAllBySecretCommentId(secretComment.getId());
        }

        // Now delete all comments associated with the board
        secretCommentRepository.deleteAll(secretComments);

        // Finally, delete the board
        secretBoardRepository.deleteById(id);
        secretBoardRepository.flush();

    }
    public SecretBoard saveBoard(SecretBoardCreateDTO dto) {


        SecretBoard freeBoard = dto.toEntity();


        secretBoardRepository.save(freeBoard);

        return freeBoard;
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public SecretBoard saveBoard(SecretBoard freeBoard) {
        return secretBoardRepository.save(freeBoard);
    }


    public Optional<SecretBoard> getBoardById(Long id){
        return secretBoardRepository.findById(id);
    }

    public List<SecretBoard> readBoardAll(){
        return secretBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        secretBoardRepository.deleteById(id);
    }


    public SecretBoard likeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(true); // Set like status to true
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard unlikeBoard(Long id) {
        SecretBoard freeBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freeBoard.setLikeStatus(false); // Set like status to false
        return secretBoardRepository.save(freeBoard);
    }

    @Transactional
    public SecretBoard updateBoard(Long id, SecretBoardCreateDTO dto) {
        SecretBoard secretBoard = secretBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        secretBoard.update(dto.title(), dto.content(), dto.author());
        return secretBoardRepository.save(secretBoard);
    }

}
