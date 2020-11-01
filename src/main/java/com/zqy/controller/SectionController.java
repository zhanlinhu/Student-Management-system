package com.zqy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqy.entity.*;
import com.zqy.service.*;
import com.zqy.service.SectionService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/section")
public class SectionController {

    private static final String LIST = "section/list";
    private static final String ADD = "section/add";
    private static final String UPDATE = "section/update";

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;

    @GetMapping("/add")
    public String create(ModelMap modelMap,Integer clazzId){
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        modelMap.put("teachers",teachers);
        modelMap.put("courses",courses);
        modelMap.put("clazzId",clazzId);
        return ADD;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody Section section){
        int result = sectionService.create(section);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = sectionService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = sectionService.delete(ids);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody Section section){
        int result = sectionService.update(section);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap){
        Section section = sectionService.detail(id);
        modelMap.addAttribute("section",section);
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        modelMap.put("teachers",teachers);
        modelMap.put("courses",courses);
        return UPDATE;
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody Section section){
        List<Section> sections = sectionService.query(section);
        List<Course> courses = courseService.query(null);
        List<Teacher> teachers = teacherService.query(null);
        Integer count = sectionService.count(section);
        sections.forEach(section1 -> {

            courses.forEach(course -> {
                if(section1.getCourseId() == course.getId().intValue()){
                    section1.setCourse(course);
                }
            });
            teachers.forEach(teacher -> {
                if(section1.getTeacherId() == teacher.getId().intValue()){
                    section1.setTeacher(teacher);
                }
            });

        });
        return MapControll.getInstance().success().page(sections,count).getMap();


//        List<Section> list = sectionService.query(section);
//        List<Subject> subjects = subjectService.query(null);
//        Integer count = sectionService.count(section);
//        return MapControll.getInstance().success().page(list,count).getMap();
    }

    @GetMapping("/list")
    public String list(ModelMap modelMap){
        return LIST;
    }

    @PostMapping("/tree")
    @ResponseBody
    public List<Map> tree(){
        List<Subject> subjects = subjectService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        List<Map> list = new ArrayList<>();
        subjects.forEach(subject -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",subject.getId());
            map.put("name",subject.getSubjectName());
            map.put("parentId",0);
            List<Map<String,Object>> childrenList = new ArrayList<>();
            clazzes.forEach(clazz -> {
                if(subject.getId() == clazz.getSubjectId()){
                    Map<String,Object> children = new HashMap<>();
                    children.put("id",clazz.getId());
                    children.put("name",clazz.getClazzName());
                    children.put("parentId",subject.getId());
                    childrenList.add(children);
                }
            });
            map.put("children",childrenList);
            list.add(map);
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/student_section")
    public String student_section(){
        return "section/student_section";
    }

    @PostMapping("/query_student_section")
    @ResponseBody
    public Map<String,Object> query_student_select(HttpSession session){
        //获取Student
        Student param = (Student) session.getAttribute("user");
        //按照学生ID查询班级设置课程信息
        List<Section> sections = sectionService.queryByStudent(param.getId());
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联对象
        sections.forEach(section -> {
            teachers.forEach(teacher -> {
                if(section.getTeacherId() == teacher.getId().intValue()){
                    section.setTeacher(teacher);
                }
            });
            courses.forEach(course -> {
                if(section.getCourseId() == course.getId().intValue()){
                    section.setCourse(course);
                }
            });
            clazzes.forEach(clazz -> {
                if(section.getClazzId() == clazz.getId().intValue()){
                    section.setClazz(clazz);
                }
            });
        });
        return  MapControll.getInstance().success().add("data",sections).getMap(); //这里塞过去的是一个对象  这个对象里面有好多数据
    }


    @GetMapping("/teacher_section")
    public String teacher_section(){
        return "section/teacher_section";
    }

    @PostMapping("/query_teacher_section")
    @ResponseBody
    public Map<String,Object> query_teacher_section(HttpSession session){
        //获取Teacher
        Teacher param = (Teacher) session.getAttribute("user");
        //按照学生ID查询班级设置课程信息
        List<Section> sections = sectionService.queryByTeacher(param.getId());
        List<Course> courses = courseService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联对象
        sections.forEach(section -> {
            courses.forEach(course -> {
                if(section.getCourseId() == course.getId().intValue()){
                    section.setCourse(course);
                }
            });
            clazzes.forEach(clazz -> {
                if(section.getClazzId() == clazz.getId().intValue()){
                    section.setClazz(clazz);
                }
            });
        });
        return  MapControll.getInstance().success().add("data",sections).getMap();
    }

    /**
     * 老师评分
     * @return
     */
    @GetMapping("/teacher_student_score")
    public String teacher_student_score(Integer courseId,Integer sectionId,ModelMap modelMap){
        List<HashMap> list = studentService.querySelectStudent(courseId, sectionId);
        modelMap.put("list",list);
        modelMap.put("sectionId",sectionId);
        modelMap.put("courseId",courseId);
        return "section/teacher_student_score";
    }

    @PostMapping("/teacher_student_score")
    @ResponseBody
    public Map<String,Object> teacher_student_score(Integer courseId,Integer sectionId,String stuIds,String scores){
        int flag = scoreService.update(courseId, sectionId, stuIds, scores);
        if(flag<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }


}
