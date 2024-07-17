package com.practice.hello.freshman.controller;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.service.FreeBoardService;
import com.practice.hello.freshman.dto.FreshmanBoardCreateDTO;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.service.FreshmanBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController //@Controller+ @ResponseBody

@RequiredArgsConstructor   //boardService 생성자 만들 필요없다
// url 종류가 여러가지 이므로 api용이라 명시 해주기 위해 api
// 일일이 적는거를 생략하기 위해 request mapping
@RequestMapping("/api/freshmanboard")
@CrossOrigin(origins = "http://localhost:5173")
public class FreshmanBoardController {



    private final FreshmanBoardService freshmanBoardService;



    @PostMapping("/save")
    public ResponseEntity<Freshman> saveBoard(@RequestBody FreshmanBoardCreateDTO dto) {

        Freshman savedFreshmanBoard = freshmanBoardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFreshmanBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<Freshman>> readBoard() {

       List<Freshman> freshman = freshmanBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(freshman);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<Freshman>> readBoard(@PathVariable Long id) {

        Optional<Freshman> board = freshmanBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            freshmanBoardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<Freshman> updateBoard(@PathVariable Long id, @RequestBody FreshmanBoardCreateDTO dto) {
        try {
            Freshman updatedFreshmanBoard = freshmanBoardService.updateBoard(id, dto);
            return ResponseEntity.ok(updatedFreshmanBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<Freshman> likeBoard(@PathVariable Long id) {
        try {
            Freshman updatedFreshmanBoard = freshmanBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreshmanBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<Freshman> unlikeBoard(@PathVariable Long id) {
        try {
            Freshman updatedFreshmanBoard = freshmanBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreshmanBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
