package com.practice.hello.secretboard.controller;



import com.practice.hello.secretboard.dto.SecretBoardCreateDTO;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.service.SecretBoardService;
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
@RequestMapping("/api/secretboard")
@CrossOrigin(origins = "http://localhost:5173")
public class SecretBoardController {



    private final SecretBoardService secretBoardService;



    @PostMapping("/save")
    public ResponseEntity<SecretBoard> saveBoard(@RequestBody SecretBoardCreateDTO dto) {

        SecretBoard savedSecretBoard = secretBoardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body( savedSecretBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<SecretBoard>> readBoard() {

       List<SecretBoard> secretBoard= secretBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(secretBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<SecretBoard>> readBoard(@PathVariable Long id) {

        Optional<SecretBoard> board =  secretBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            secretBoardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<SecretBoard> updateBoard(@PathVariable Long id, @RequestBody SecretBoardCreateDTO dto) {
        try {
            SecretBoard updatedFreeBoard = secretBoardService.updateBoard(id, dto);
            return ResponseEntity.ok(updatedFreeBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<SecretBoard> likeBoard(@PathVariable Long id) {
        try {
            SecretBoard updatedFreeBoard = secretBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreeBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<SecretBoard> unlikeBoard(@PathVariable Long id) {
        try {
            SecretBoard updatedFreeBoard = secretBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreeBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
