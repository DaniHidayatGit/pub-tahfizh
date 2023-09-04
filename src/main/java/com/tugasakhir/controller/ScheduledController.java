package com.tugasakhir.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author : Dani Hidayat
 * @email : dani.hidayat@iconpln.co.id
 * @date : 01/09/2023
 */
@RestController
@RequestMapping("/scheduled")
@RequiredArgsConstructor
public class ScheduledController {

    private static final String SCHEDULED_KEY = "scheduledTasks";

    private final ScheduledAnnotationBeanPostProcessor postProcessor;

//    @GetMapping("/stop")
//    public String stopSchedular(){
//        postProcessor.postProcessBeforeDestruction("",SCHEDULED_KEY);
//        return "STOP";
//    }
//
//    @GetMapping("/start")
//    public String startSchedular(){
//        postProcessor.postProcessAfterInitialization("",SCHEDULED_KEY);
//        return "STOP";
//    }
//
//    @GetMapping("/ifExists")
//    public String ifExists() {
//        Set<ScheduledTask> task = postProcessor.getScheduledTasks();
//        if(task.isEmpty()){
//            return "No schedular task running";
//        }else{
//            return task.toString();
//        }
//    }
}
