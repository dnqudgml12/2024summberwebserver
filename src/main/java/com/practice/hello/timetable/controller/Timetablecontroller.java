package com.practice.hello.timetable.controller;


import com.practice.hello.timetable.dto.TimetableCreatedDTO;
import com.practice.hello.timetable.entity.Timetable;
import com.practice.hello.timetable.service.Timetableservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/timetable")
@CrossOrigin(origins = "http://localhost:5173")
public class Timetablecontroller {

    private  final Timetableservice timetableservice;
    @PostMapping("/save")

    public ResponseEntity<Timetable> saveTable(@RequestBody TimetableCreatedDTO dto){
        Timetable savedTimetable = timetableservice.saveTimetable(dto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(savedTimetable);
    }

    @GetMapping("/read")

    public ResponseEntity<List<Timetable>> readTable(){
        List<Timetable> timetable= timetableservice.readTableAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(timetable);
    }

    @DeleteMapping("delete/{id}")
    public  ResponseEntity<Void> deleteTable(@PathVariable Long id){
        try{
            timetableservice.deleteTimetable(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Timetable> updateTable(@PathVariable Long id, @RequestBody TimetableCreatedDTO dto){
        try{
            Timetable updateTimetable= timetableservice.updateTable(id,dto);
            return  ResponseEntity.ok(updateTimetable);

        }catch (RuntimeException e){

            return  ResponseEntity.notFound().build();
        }
    }







}
