package com.practice.hello.graduateboard.controller;



import com.practice.hello.graduateboard.dto.GradutateBoardCreateDTO;
import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.service.GradutateBoardService;
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
@RequestMapping("/api/graduateboard")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class GraduateBoardController {



    private final GradutateBoardService gradutateBoardService;



    @PostMapping("/save")
    public ResponseEntity<GraduateBoard > saveBoard(@RequestBody GradutateBoardCreateDTO dto, Principal principal) {
        String uId = principal.getName();
       GraduateBoard savedGraduateBoard = gradutateBoardService.saveBoard(dto, uId);

        return ResponseEntity.status(HttpStatus.CREATED).body( savedGraduateBoard);
    }


    @GetMapping("/read")

    public ResponseEntity<List<GraduateBoard>> readBoard() {

       List<GraduateBoard> graduateBoard= gradutateBoardService.readBoardAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(graduateBoard);
    }
    @GetMapping("/read/{id}")

/*
@PathVariable 사용: URL에 노출될 수 있는 ID와 같은 간단한 값의 경우.
@RequestBody 사용: 요청 본문으로 전송되는 복잡한 개체(DTO)의 경우.
*/
    public ResponseEntity<Optional<GraduateBoard>> readBoard(@PathVariable Long id) {

        Optional<GraduateBoard> board =  gradutateBoardService.getBoardById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        System.out.println("Principal email: " + uId); // Debug statement
        try {
            gradutateBoardService.deleteBoardAndAdjustIds(id, uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<GraduateBoard> updateBoard(@PathVariable Long id, @RequestBody GradutateBoardCreateDTO dto, Principal principal) {
        try {
            String uId = principal.getName();
            log.debug("Updating board with ID: {}, by user: {}", id, uId); // 디버그 로그 추가
            GraduateBoard updatedGraduateBoard = gradutateBoardService.updateBoard(id, dto, uId);
            log.debug("Updated board details: {}", updatedGraduateBoard); // 디버그 로그 추가
            return ResponseEntity.ok(updatedGraduateBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/like/{id}")
    public ResponseEntity<GraduateBoard> likeBoard(@PathVariable Long id) {
        try {
            GraduateBoard updatedGraduateBoard = gradutateBoardService.likeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedGraduateBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<GraduateBoard> unlikeBoard(@PathVariable Long id) {
        try {
            GraduateBoard updatedGraduateBoard = gradutateBoardService.unlikeBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedGraduateBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/read/paginated")
    public ResponseEntity<Page<GraduateBoard>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<GraduateBoard> graduateBoardPage = gradutateBoardService.readBoardAll(pageable);


        if (page == 0 && graduateBoardPage.getContent().size() < size) {
            // If on the first page and the number of items is less than the size, return the content
            return ResponseEntity.ok(graduateBoardPage);
        } else if (graduateBoardPage.hasContent()) {
            return ResponseEntity.ok(graduateBoardPage);
        } else {
            return ResponseEntity.noContent().build();
        }
    }




}
