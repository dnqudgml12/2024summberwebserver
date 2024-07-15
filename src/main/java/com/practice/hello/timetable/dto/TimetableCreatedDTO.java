package com.practice.hello.timetable.dto;

import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.timetable.entity.Timetable;

public record TimetableCreatedDTO(String subject, String professor, String day, String startTime,
                                  String endTime, String location, String color) {


    public Timetable toEntity() {

        return Timetable.builder()
                .subject(subject)
                .professor(professor)

                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .color(color)
                .build();

    }
}
