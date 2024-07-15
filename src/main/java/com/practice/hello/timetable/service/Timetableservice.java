package com.practice.hello.timetable.service;


import com.practice.hello.timetable.dto.TimetableCreatedDTO;
import com.practice.hello.timetable.entity.Timetable;
import com.practice.hello.timetable.repository.TimetableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service // Service annotation
public class Timetableservice {

    private final TimetableRepository timetableRepository;


    @Transactional
    public void deleteTimetable(Long id) {

        timetableRepository.deleteById(id);
        timetableRepository.flush();
    }

    public Timetable saveTimetable(TimetableCreatedDTO dto){

        Timetable timetable= dto.toEntity();

        timetableRepository.save(timetable);


        return  timetable;


    }

    public List<Timetable> readTableAll(){return timetableRepository.findAll();}

  @Transactional
    public  Timetable updateTable(Long id, TimetableCreatedDTO dto){

        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Table not found"));

        timetable.update(dto.subject(),dto.professor(),dto.day(),dto.startTime(),dto.endTime(),dto.location());

        return  timetableRepository.save(timetable);

  }
}

