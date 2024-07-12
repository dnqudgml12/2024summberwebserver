package com.practice.hello.controller.board;


import com.practice.hello.dto.boardreq.BoardCreateDTO;
import com.practice.hello.dto.boardreq.CommentCreateDTO;
import com.practice.hello.dto.boardreq.ReplyCreatedDTO;
import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;
import com.practice.hello.entity.board.Reply;
import com.practice.hello.service.board.BoardService;
import com.practice.hello.service.board.CommentService;
import com.practice.hello.service.board.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController //@Controller+ @ResponseBody

@RequiredArgsConstructor   //boardService 생성자 만들 필요없다
// url 종류가 여러가지 이므로 api용이라 명시 해주기 위해 api
// 일일이 적는거를 생략하기 위해 request mapping
@RequestMapping("/api/board")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {



    private final BoardService boardService;

    private final CommentService commentService;

    private final ReplyService replyService;

    @PostMapping("/save")
    public ResponseEntity<Board> saveBoard(@RequestBody BoardCreateDTO dto) {

        Board savedBoard = boardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<Board>> readBoard() {

       List<Board> board = boardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<Board>> readBoard(@PathVariable Long id) {

        Optional<Board> board = boardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/{boardId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long boardId, @RequestBody CommentCreateDTO dto) {
        try {
            Comment savedComment = commentService.saveComment(dto, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ResponseEntity<Reply> addReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody ReplyCreatedDTO dto) {
        try {
            Reply savedReply = replyService.saveReply(dto, commentId, boardId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId) {
        try {
            replyService.deleteReply(replyId, commentId, boardId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardCreateDTO dto) {
        Optional<Board> optionalBoard = boardService.getBoardById(id);
        if (optionalBoard.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Board board = optionalBoard.get();
        board.update(dto.title(), dto.content(), dto.author());  // BoardCreateDTO에서 record이므로 이 형태로 getter 사용
        Board updatedBoard = boardService.saveBoard(board);
        return ResponseEntity.ok(updatedBoard);
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<Board> likeBoard(@PathVariable Long id) {
        try {
            Board updatedBoard = boardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<Board> unlikeBoard(@PathVariable Long id) {
        try {
            Board updatedBoard = boardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
