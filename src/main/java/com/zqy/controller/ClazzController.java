package com.zqy.controller;

import com.zqy.entity.Clazz;
import com.zqy.entity.Subject;
import com.zqy.service.ClazzService;
import com.zqy.service.SubjectService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clazz")
public class ClazzController {

    private static final String LIST = "clazz/list";
    private static final String ADD = "clazz/add";
    private static final String UPDATE = "clazz/update";

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/add")
    public String create(ModelMap modelMap){
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("subjects",subjects);
        return ADD;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody Clazz clazz){
        int result = clazzService.create(clazz);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = clazzService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = clazzService.delete(ids);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody Clazz clazz){
        int result = clazzService.update(clazz);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap){
        Clazz clazz = clazzService.detail(id);
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("clazz",clazz);
        modelMap.addAttribute("subjects",subjects);
        return UPDATE;
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody Clazz clazz){
        List<Clazz> list = clazzService.query(clazz);
        List<Subject> subjects = subjectService.query(null);
        //设置subject
        list.forEach(entity->{
            subjects.forEach(subject -> {
                if(entity.getSubjectId() == subject.getId()){
                    entity.setSubject(subject);
                }
            });
        });
        Integer count = clazzService.count(clazz);
        return MapControll.getInstance().success().page(list,count).getMap();//这个page是总个数 不是分页的
    }

    @GetMapping("/list")
    public String list(){
        return LIST;
    }

}
