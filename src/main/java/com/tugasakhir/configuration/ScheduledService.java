package com.tugasakhir.configuration;

import com.tugasakhir.controller.ScheduledController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;

/**
 * @author : Dani Hidayat
 * @email : dani.hidayat@iconpln.co.id
 * @date : 01/09/2023
 */
@Service
@Transactional
public class ScheduledService extends DBQueryHandler{
    public ScheduledService(DataSource dataSource){
        this.setDataSource(dataSource);
    }


//    @Scheduled(cron = "* * * * *")
//    public void myTask(){
//        System.out.println("Scheduled on");
//    }

}
