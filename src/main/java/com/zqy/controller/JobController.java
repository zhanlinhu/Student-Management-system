package com.zqy.controller;

import com.zqy.entity.Job;
import com.zqy.service.JobService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(Job job){
        int result = jobService.create(job);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = jobService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(Job job){
        int result = jobService.update(job);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap){

        Job job = jobService.detailByStudent(id);
        modelMap.put("stuId",id);
        if(job == null){
            return "job/add";
        }else{
            modelMap.put("job",job);
            return "job/update";
        }
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(Job job){
        List<Job> list = jobService.query(job);
        return MapControll.getInstance().success().put("data",list).getMap();
    }


}
