package com.practice.hello.information.controller;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.service.FreeBoardService;
import com.practice.hello.information.dto.InformationBoardCreateDTO;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.service.InformationBoardService;
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
@RequestMapping("/api/informationboard")
@CrossOrigin(origins = "http://localhost:5173")
public class InformationBoardController {



    private final InformationBoardService informationBoardService;



    @PostMapping("/save")
    public ResponseEntity<InformationBoard> saveBoard(@RequestBody InformationBoardCreateDTO dto) {

        InformationBoard savedInformationBoard = informationBoardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedInformationBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<InformationBoard>> readBoard() {

       List<InformationBoard> informationBoard = informationBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(informationBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<InformationBoard>> readBoard(@PathVariable Long id) {

        Optional<InformationBoard> board = informationBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            informationBoardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<InformationBoard> updateBoard(@PathVariable Long id, @RequestBody InformationBoardCreateDTO dto) {
        try {
            InformationBoard updatedInformationBoard = informationBoardService.updateBoard(id, dto);
            return ResponseEntity.ok(updatedInformationBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<InformationBoard> likeBoard(@PathVariable Long id) {
        try {
            InformationBoard updatedInformationBoard = informationBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedInformationBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<InformationBoard> unlikeBoard(@PathVariable Long id) {
        try {
            InformationBoard updatedInformationBoard = informationBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedInformationBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
