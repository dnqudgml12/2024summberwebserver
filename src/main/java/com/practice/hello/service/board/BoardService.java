package com.practice.hello.service.board;


import com.practice.hello.dto.boardreq.BoardCreateDTO;
import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;
import com.practice.hello.repository.board.BoardRepository;
import com.practice.hello.repository.board.CommentRepository;
import com.practice.hello.repository.board.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
public class BoardService {


    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    private final ReplyRepository replyRepository;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id) {
        // First delete all comments associated with the board

        // First, delete all replies associated with each comment of the board
        List<Comment> comments = commentRepository.findByBoardId(id);
        for (Comment comment : comments) {
            replyRepository.deleteAllByCommentId(comment.getId());
        }

        // Now delete all comments associated with the board
        commentRepository.deleteAll(comments);

        // Finally, delete the board
        boardRepository.deleteById(id);
        boardRepository.flush();

    }
    public Board saveBoard(BoardCreateDTO dto) {


        Board board = dto.toEntity();


        boardRepository.save(board);

        return board;
    }

    //update시에는 entity 형태로 받아서 저장해야 한다
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }


    public Optional<Board> getBoardById(Long id){
        return boardRepository.findById(id);
    }

    public List<Board> readBoardAll(){
        return boardRepository.findAll();
    }

    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }


    public Board likeBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        board.setLikeStatus(true); // Set like status to true
        return boardRepository.save(board);
    }

    @Transactional
    public Board unlikeBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        board.setLikeStatus(false); // Set like status to false
        return boardRepository.save(board);
    }



}
