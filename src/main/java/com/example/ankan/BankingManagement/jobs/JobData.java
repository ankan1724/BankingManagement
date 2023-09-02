package com.example.ankan.BankingManagement.jobs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class JobData {

    private String jobName;

    private String jobGroup;

    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    private LocalDateTime startTime;

    private int counter;

    private int gapDuration;


}
