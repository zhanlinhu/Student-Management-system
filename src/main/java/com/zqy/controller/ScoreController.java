package com.zqy.controller;

import com.zqy.entity.Score;
import com.zqy.entity.Student;
import com.zqy.service.ScoreService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(String sectionIds, String courseIds, HttpSession session){
        Student student = (Student) session.getAttribute("user");
        int result = scoreService.create(sectionIds,courseIds,student.getId());
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = scoreService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(Score score){
        int result = scoreService.update(score);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/detail/{id}")
    @ResponseBody
    public Map<String,Object> detail(@PathVariable("id") Integer id){
        Score score = scoreService.detail(id);
        if(score == null){
            return MapControll.getInstance().nodata().getMap();
        }
        return MapControll.getInstance().success().put("data",score).getMap();
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(Score score){
        List<Score> list = scoreService.query(score);
        return MapControll.getInstance().success().put("data",list).getMap();
    }

    @GetMapping("/student_score")
    public String student_score(){
        return "score/student_score";
    }

    @PostMapping("/student_score")
    @ResponseBody
    public Map<String,Object> student_score(Score score,HttpSession session){
        Student student = (Student) session.getAttribute("user");
        List<HashMap> mapList = scoreService.queryScoreByStudent(student.getId());
        return MapControll.getInstance().success().put("data",mapList).getMap();
    }


}
