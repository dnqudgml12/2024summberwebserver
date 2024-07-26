package com.practice.hello.freshman.controller;


import com.practice.hello.freshman.dto.FreshmanBoardCreateDTO;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.service.FreshmanBoardService;
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
@RequestMapping("/api/freshmanboard")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class FreshmanBoardController {



    private final FreshmanBoardService freshmanBoardService;



    @PostMapping("/save")
    public ResponseEntity<Freshman> saveBoard(@RequestBody FreshmanBoardCreateDTO dto, Principal principal) {
        String uId = principal.getName();
        Freshman savedFreshmanBoard = freshmanBoardService.saveBoard(dto, uId);

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
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        System.out.println("Principal email: " + uId); // Debug statement
        try {
            freshmanBoardService.deleteBoardAndAdjustIds(id, uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<Freshman> updateBoard(@PathVariable Long id, @RequestBody FreshmanBoardCreateDTO dto, Principal principal) {
        try {
            String uId = principal.getName();
            log.debug("Updating board with ID: {}, by user: {}", id, uId); // 디버그 로그 추가
            Freshman updatedFreshmanBoard = freshmanBoardService.updateBoard(id, dto, uId);
            log.debug("Updated board details: {}", updatedFreshmanBoard); // 디버그 로그 추가
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

    @GetMapping("/read/paginated")
    public ResponseEntity<Page<Freshman>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Freshman> freshmanBoardPage = freshmanBoardService.readBoardAll(pageable);

        if (page == 0 && freshmanBoardPage.getContent().size() < size) {
            // If on the first page and the number of items is less than the size, return the content
            return ResponseEntity.ok(freshmanBoardPage);
        } else if (freshmanBoardPage.hasContent()) {
            return ResponseEntity.ok(freshmanBoardPage);
        } else {
            return ResponseEntity.noContent().build();
        }
    }





}
