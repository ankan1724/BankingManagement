package com.example.ankan.BankingManagement.jobs;

import com.example.ankan.BankingManagement.Dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
public class ScheduledJob extends QuartzJobBean {

    @Autowired
    UserDao dao;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap=context.getMergedJobDataMap();
        log.info(dao.findAll().toString());
        }
    }


