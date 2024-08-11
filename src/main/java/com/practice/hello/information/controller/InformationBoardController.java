package com.practice.hello.information.controller;


import com.practice.hello.information.dto.InformationBoardCreateDTO;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.service.InformationBoardService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/informationboard")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
public class InformationBoardController {



    private final InformationBoardService informationBoardService;



    @PostMapping("/save")
    public ResponseEntity<InformationBoard> saveBoard( @RequestPart("dto")  InformationBoardCreateDTO dto, Principal principal, @RequestPart(value = "file", required = false) MultipartFile file) {
        String uId = principal.getName();
        InformationBoard savedInformationBoard = informationBoardService.saveBoard(dto, uId,file);

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
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,Principal principal) {
        String uId = principal.getName();
        System.out.println("Principal email: " + uId); // Debug statement
        try {
            informationBoardService.deleteBoardAndAdjustIds(id, uId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<InformationBoard> updateBoard(@PathVariable Long id, @RequestPart("dto")InformationBoardCreateDTO dto, Principal principal, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String uId = principal.getName();
            InformationBoard updatedInformationBoard = informationBoardService.updateBoard(id, dto, uId,file);
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

    @GetMapping("/read/paginated")
    public ResponseEntity<Page<InformationBoard>> readPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,// Use createdAt as the default sort field
            @RequestParam(defaultValue = "desc") String sortDir)// Default to descending order
    {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<InformationBoard> informationBoardPage = informationBoardService.readBoardAll(pageable);

        return ResponseEntity.ok(informationBoardPage);
    }




}
