package com.practice.hello.freeboard.controller;


import com.practice.hello.freeboard.dto.FreeBoardCreateDTO;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController //@Controller+ @ResponseBody

@RequiredArgsConstructor   //boardService 생성자 만들 필요없다
// url 종류가 여러가지 이므로 api용이라 명시 해주기 위해 api
// 일일이 적는거를 생략하기 위해 request mapping
@RequestMapping("/api/freeboard")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class FreeBoardController {



    private final FreeBoardService freeBoardService;



    @PostMapping("/save")
    public ResponseEntity<FreeBoard> saveBoard(@RequestBody FreeBoardCreateDTO dto, Principal principal) {
        String uId = principal.getName();
        FreeBoard savedFreeBoard = freeBoardService.saveBoard(dto, uId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<FreeBoard>> readBoard() {

       List<FreeBoard> freeBoard = freeBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(freeBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<FreeBoard>> readBoard(@PathVariable Long id) {

        Optional<FreeBoard> board = freeBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        System.out.println("Principal email: " + uId); // Debug statement
        try {
            freeBoardService.deleteBoardAndAdjustIds(id, uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<FreeBoard> updateBoard(@PathVariable Long id, @RequestBody FreeBoardCreateDTO dto, Principal principal) {
        try {
            String uId = principal.getName();
            log.debug("Updating board with ID: {}, by user: {}", id, uId); // 디버그 로그 추가
            FreeBoard updatedFreeBoard = freeBoardService.updateBoard(id, dto, uId);
            log.debug("Updated board details: {}", updatedFreeBoard); // 디버그 로그 추가
            return ResponseEntity.ok(updatedFreeBoard);
        } catch (RuntimeException e) {
            log.error("Error updating board", e); // 예외 로그 추가
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/like/{id}")
    public ResponseEntity<FreeBoard> likeBoard(@PathVariable Long id) {
        try {
            FreeBoard updatedFreeBoard = freeBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreeBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<FreeBoard> unlikeBoard(@PathVariable Long id) {
        try {
            FreeBoard updatedFreeBoard = freeBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedFreeBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //서버에서 요청으로 페이지를 나누어 보내는 형식
    @GetMapping("/read/paginated")
    public ResponseEntity<Page<FreeBoard>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<FreeBoard> freeBoardPage = freeBoardService.readBoardAll(pageable);

        if (page == 0 && freeBoardPage.getContent().size() < size) {
            // If on the first page and the number of items is less than the size, return the content
            return ResponseEntity.ok(freeBoardPage);
        } else if (freeBoardPage.hasContent()) {
            return ResponseEntity.ok(freeBoardPage);
        } else {
            return ResponseEntity.noContent().build();
        }

    }




}
