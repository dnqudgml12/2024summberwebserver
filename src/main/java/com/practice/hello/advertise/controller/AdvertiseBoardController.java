package com.practice.hello.advertise.controller;


import com.practice.hello.advertise.dto.AdvertiseBoardCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.service.AdvertiseBoardService;
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
@RequestMapping("/api/advertiseboard")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertiseBoardController {



    private final AdvertiseBoardService advetiseBoardService;



    @PostMapping("/save")
    public ResponseEntity<AdvertiseBoard> saveBoard(@RequestBody AdvertiseBoardCreateDTO dto) {

        AdvertiseBoard savedAdvertiseBoard = advetiseBoardService.saveBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertiseBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<AdvertiseBoard>> readBoard() {

       List<AdvertiseBoard> advertiseBoard = advetiseBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(advertiseBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<AdvertiseBoard>> readBoard(@PathVariable Long id) {

        Optional<AdvertiseBoard> board = advetiseBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            advetiseBoardService.deleteBoardAndAdjustIds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<AdvertiseBoard> updateBoard(@PathVariable Long id, @RequestBody AdvertiseBoardCreateDTO dto) {
        try {
            AdvertiseBoard updatedAdvertiseBoard = advetiseBoardService.updateBoard(id, dto);
            return ResponseEntity.ok(updatedAdvertiseBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<AdvertiseBoard> likeBoard(@PathVariable Long id) {
        try {
            AdvertiseBoard updatedAdvertiseBoard = advetiseBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAdvertiseBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<AdvertiseBoard> unlikeBoard(@PathVariable Long id) {
        try {
            AdvertiseBoard updatedAdvertiseBoard = advetiseBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAdvertiseBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }





}
