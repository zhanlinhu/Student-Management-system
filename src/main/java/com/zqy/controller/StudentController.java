package com.zqy.controller;

import com.zqy.entity.*;
import com.zqy.entity.Student;
import com.zqy.service.ClazzService;
import com.zqy.service.StudentService;
import com.zqy.service.SubjectService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {


    private static final String LIST = "student/list";
    private static final String ADD = "student/add";
    private static final String UPDATE = "student/update";

    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/add")
    public String create(ModelMap modelMap){
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("subjects",subjects);
        return ADD;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody Student student){
        student.setStatus(Student.StatusType.type_1);
        int result = studentService.create(student);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = studentService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = studentService.delete(ids);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody Student student){
        int result = studentService.update(student);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap){
        Student student = studentService.detail(id);
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("student",student);
        modelMap.addAttribute("subjects",subjects);
        return UPDATE;
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody Student student){
        List<Student> list = studentService.query(student);
        List<Subject> subjects = subjectService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联关系
        list.forEach(entity->{
            subjects.forEach(subject -> {
                if(subject.getId() == entity.getSubjectId()){
                    entity.setSubject(subject);
                }
            });
            clazzes.forEach(clazz -> {
                if(clazz.getId() == entity.getClazzId()){
                    entity.setClazz(clazz);
                }
            });
        });
        Integer count = studentService.count(student);
        return MapControll.getInstance().success().page(list,count).getMap();
    }

    @GetMapping("/list")
    public String list(){
        return LIST;
    }


    @GetMapping("/info")
    public String info(HttpSession session,ModelMap modelMap){
        //获取Student
        Student param = (Student) session.getAttribute("user");
        Student student = studentService.detail(param.getId());
        modelMap.put("student",student);
        return "student/info";
    }

    @GetMapping("/teacher_student")
    public String teacher_student(HttpSession session,ModelMap modelMap){
        Teacher teacher = (Teacher) session.getAttribute("user");
        List<Clazz> clazzes = clazzService.query(null);
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("clazzes",clazzes);
        modelMap.addAttribute("subjects",subjects);
        modelMap.addAttribute("teacherId",teacher.getId());
        return "student/teacher_student";
    }
    @PostMapping("/teacher_student")
    @ResponseBody
    public Map<String,Object> teacher_student(Integer teacherId,Integer clazzId,Integer subjectId){
        List<Student> students = studentService.queryStudentByTeacher(teacherId, clazzId, subjectId);
        List<Subject> subjects = subjectService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联关系
        students.forEach(entity->{
            subjects.forEach(subject -> {
                if(subject.getId() == entity.getSubjectId()){
                    entity.setSubject(subject);
                }
            });
            clazzes.forEach(clazz -> {
                if(clazz.getId() == entity.getClazzId()){
                    entity.setClazz(clazz);
                }
            });
        });
        return MapControll.getInstance().success().add("data",students).getMap();
    }



}
