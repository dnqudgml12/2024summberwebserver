package com.practice.hello.advertise.controller;


import com.practice.hello.advertise.dto.AdvertiseBoardCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.service.AdvertiseBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController //@Controller+ @ResponseBody

@RequiredArgsConstructor   //boardService 생성자 만들 필요없다
// url 종류가 여러가지 이므로 api용이라 명시 해주기 위해 api
// 일일이 적는거를 생략하기 위해 request mapping
@RequestMapping("/api/advertiseboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
//배포 후에
//@CrossOrigin(origins = "https://2024-summberwebfront.vercel.app/")
@Slf4j
public class AdvertiseBoardController {



    private final AdvertiseBoardService advetiseBoardService;



    @PostMapping("/save")
    public ResponseEntity<AdvertiseBoard> saveBoard(
            @RequestPart("dto") AdvertiseBoardCreateDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Principal principal) {
        String uId = principal.getName();
        AdvertiseBoard savedAdvertiseBoard = advetiseBoardService.saveBoard(dto, uId,file);

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
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        System.out.println("Principal email: " + uId); // Debug statement
        try {
            advetiseBoardService.deleteBoardAndAdjustIds(id,uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<AdvertiseBoard> updateBoard(@PathVariable Long id,  @RequestPart("dto")AdvertiseBoardCreateDTO dto, Principal principal,
                                                      @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String uId = principal.getName();
            log.debug("Updating board with ID: {}, by user: {}", id, uId); // 디버그 로그 추가
            AdvertiseBoard updatedAdvertiseBoard = advetiseBoardService.updateBoard(id, dto,uId,file);
            log.debug("Updated board details: {}", updatedAdvertiseBoard); // 디버그 로그 추가
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

    @GetMapping("/read/paginated")
    public ResponseEntity<Page<AdvertiseBoard>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<AdvertiseBoard> advertiseBoardPage = advetiseBoardService.readBoardAll(pageable);


        if (page == 0 && advertiseBoardPage.getContent().size() < size) {
            // If on the first page and the number of items is less than the size, return the content
            return ResponseEntity.ok(advertiseBoardPage);
        } else if (advertiseBoardPage.hasContent()) {
            return ResponseEntity.ok(advertiseBoardPage);
        } else {
            return ResponseEntity.ok(advertiseBoardPage);
        }
    }





}