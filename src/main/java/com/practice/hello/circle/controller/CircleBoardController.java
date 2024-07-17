package com.practice.hello.circle.controller;


import com.practice.hello.circle.dto.CircleBoardCreateDTO;
import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.service.CircleBoardService;
import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.service.FreeBoardService;
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
@RequestMapping("/api/circleboard")
@CrossOrigin(origins = "http://localhost:5173")
public class CircleBoardController {



    private final CircleBoardService circleBoardService;



    @PostMapping("/save")
    public ResponseEntity<CircleBoard> saveBoard(@RequestBody CircleBoardCreateDTO dto) {

        CircleBoard savedCircleBoard = circleBoardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCircleBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<CircleBoard>> readBoard() {

       List<CircleBoard> circleBoard = circleBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(circleBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<CircleBoard>> readBoard(@PathVariable Long id) {

        Optional<CircleBoard> board = circleBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            circleBoardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<CircleBoard> updateBoard(@PathVariable Long id, @RequestBody CircleBoardCreateDTO dto) {
        try {
            CircleBoard updatedCircleBoard = circleBoardService.updateBoard(id, dto);
            return ResponseEntity.ok(updatedCircleBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<CircleBoard> likeBoard(@PathVariable Long id) {
        try {
            CircleBoard updatedCircleBoard = circleBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCircleBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<CircleBoard> unlikeBoard(@PathVariable Long id) {
        try {
            CircleBoard updatedCircleBoard = circleBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCircleBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
