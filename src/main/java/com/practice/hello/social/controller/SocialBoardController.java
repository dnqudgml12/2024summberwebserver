package com.practice.hello.social.controller;


import com.practice.hello.social.dto.SocialBoardCreateDTO;
import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.service.SocialBoardService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/socialboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class SocialBoardController {



    private final SocialBoardService socialBoardService;



    @PostMapping("/save")
    public ResponseEntity<SocialBoard> saveBoard(@RequestBody SocialBoardCreateDTO dto, Principal principal) {
        String uId = principal.getName();
        SocialBoard savedSocialBoard = socialBoardService.saveBoard(dto, uId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSocialBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<SocialBoard>> readBoard() {

       List<SocialBoard> socialBoard = socialBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(socialBoard);
    }
    @GetMapping("/read/{id}")

    public ResponseEntity<Optional<SocialBoard>> readBoard(@PathVariable Long id) {

        Optional<SocialBoard> board = socialBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        try {
            socialBoardService.deleteBoardAndAdjustIds(id, uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<SocialBoard> updateBoard(@PathVariable Long id, @RequestBody SocialBoardCreateDTO dto, Principal principal) {
        try {
            String uId = principal.getName();
            SocialBoard updatedSocialBoard = socialBoardService.updateBoard(id, dto, uId);
            return ResponseEntity.ok(updatedSocialBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<SocialBoard> likeBoard(@PathVariable Long id) {
        try {
            SocialBoard updatedSocialBoard = socialBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSocialBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<SocialBoard> unlikeBoard(@PathVariable Long id) {
        try {
            SocialBoard updatedSocialBoard = socialBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSocialBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/read/paginated")
    public ResponseEntity<Page<SocialBoard>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<SocialBoard> socialBoardPage = socialBoardService.readBoardAll(pageable);

        return ResponseEntity.ok(socialBoardPage);
    }





}
