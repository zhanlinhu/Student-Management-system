package com.zqy.controller;

import com.zqy.entity.*;
import com.zqy.service.*;
import com.zqy.utils.MD5Utils;
import com.zqy.utils.MapControll;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    SectionService sectionService;
    @Autowired
    ScoreService scoreService;

    @GetMapping("/index")
    public String login(){
        return "index";
    }

    @GetMapping("/main")
    public String main(ModelMap modelMap){
        //1、班级学生数统计
        List<Map<String,Object>> clazzList = new ArrayList<>();
        List<Clazz> clazzes = clazzService.query(null);
        List<Student> students = studentService.query(null);
        for (Clazz clazz : clazzes) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",clazz.getClazzName());
            int cnt = 0;
            for (Student student :students) {
                if(clazz.getId().intValue() == student.getClazzId()){
                    cnt++;
                }
            }
            map.put("cnt",cnt);
            clazzList.add(map);
        }
        modelMap.put("clazzList",clazzList);
        //2、课程平均成绩统计
        List<HashMap> scoreList = scoreService.queryAvgScoreBySection();
        modelMap.put("scoreList",scoreList);
        return "main";
    }

    @GetMapping("/info")
    public String info(){
        return "info";
    }

    /**
     * 首页数字统计
     * @return
     */
    @PostMapping("/sum")
    @ResponseBody
    public Map<String,Object> sum(){
        List<Clazz> clazzes = clazzService.query(null);
        List<Subject> subjects = subjectService.query(null);
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        List<Section> sections = sectionService.query(null);
        List<Student> students = studentService.query(null);
        Map<String,Object> map = MapParameter.getInstance()
                .add("clazzCnt",clazzes.size())
                .add("subjectCnt",subjects.size())
                .add("teacherCnt",teachers.size())
                .add("courseCnt",courses.size())
                .add("studentCnt",students.size())
                .add("sectionCnt",sections.size())
                .getMap();
        return MapControll.getInstance().success().add("data",map).getMap();
    }

    @GetMapping("/pwd")
    public String pwd(){
        return "pwd";
    }
    @PostMapping("/pwd")
    @ResponseBody
    public Map<String,Object> pwd(Integer id,String type,String sourcePwd,String newPwd){
        if("1".equals(type)){
            User user = userService.detail(id);
            if(user.getUserPwd().equals(MD5Utils.getMD5(sourcePwd))){
                User entity = new User();
                entity.setId(id);
                entity.setUserPwd(MD5Utils.getMD5(newPwd));
                int update = userService.update(entity);
                if(update>0){
                    return MapControll.getInstance().success().getMap();
                }else{
                    return MapControll.getInstance().error().getMap();
                }
            }else{
                return MapControll.getInstance().error("原密码错误").getMap();
            }

        }
        if("2".equals(type)){
            Teacher teacher = teacherService.detail(id);
            if(teacher.getTeacherPwd().equals(MD5Utils.getMD5(sourcePwd))){
                Teacher entity = new Teacher();
                entity.setId(id);
                entity.setTeacherPwd(MD5Utils.getMD5(newPwd));
                int update = teacherService.update(entity);
                if(update>0){
                    return MapControll.getInstance().success().getMap();
                }else{
                    return MapControll.getInstance().error().getMap();
                }
            }else{
                return MapControll.getInstance().error("原密码错误").getMap();
            }
        }
        if("3".equals(type)){
            Student student = studentService.detail(id);
            if(student.getStuPwd().equals(MD5Utils.getMD5(sourcePwd))){
                Student entity = new Student();
                entity.setId(id);
                entity.setStuPwd(MD5Utils.getMD5(newPwd));
                int update = studentService.update(entity);
                if(update>0){
                    return MapControll.getInstance().success().getMap();
                }else{
                    return MapControll.getInstance().error().getMap();
                }
            }else{
                return MapControll.getInstance().error("原密码错误").getMap();
            }

        }
        return MapControll.getInstance().error().getMap();
    }
}
