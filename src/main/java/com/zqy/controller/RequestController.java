package com.zqy.controller;

import com.zqy.entity.Request;
import com.zqy.entity.Student;
import com.zqy.entity.Teacher;
import com.zqy.entity.User;
import com.zqy.service.ClazzService;
import com.zqy.service.SubjectService;
import com.zqy.service.RequestService;
import com.zqy.utils.MapControll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/request")
public class RequestController {



    private static final String LIST = "request/list";
    private static final String ADD = "request/add";
    private static final String UPDATE = "request/update";
    private static final String AUDIT = "request/audit";

    @Autowired
    private RequestService requestService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/add")
    public String create(ModelMap modelMap){
        return ADD;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody Request request, HttpSession session){
        Student student = (Student) session.getAttribute("user");
        request.setStuId(student.getId());
        request.setStatus(Request.StatusType.status_1.getVal());
        int result = requestService.create(request);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        int result = requestService.delete(id);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = requestService.delete(ids);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody Request request){
        int result = requestService.update(request);
        if(result<=0){
            return MapControll.getInstance().error().getMap();
        }
        return MapControll.getInstance().success().getMap();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap){
        Request request = requestService.detail(id);
        modelMap.addAttribute("request",request);
        return UPDATE;
    }

    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody Request request){
        List<Request> list = requestService.query(request);
        list.forEach(entity ->{
            if(entity.getStatus() == 1){
                entity.setStatusTxt(Request.StatusType.status_1.getMsg());
            }
            if(entity.getStatus() == 2){
                entity.setStatusTxt(Request.StatusType.status_2.getMsg());
            }
            if(entity.getStatus() == 3){
                entity.setStatusTxt(Request.StatusType.status_3.getMsg());
            }
            if(entity.getStatus() == 4){
                entity.setStatusTxt(Request.StatusType.status_4.getMsg());
            }
        });

        Integer count = requestService.count(request);
        return MapControll.getInstance().success().page(list,count).getMap();
    }

    @GetMapping("/list")
    public String list(){
        return LIST;
    }


    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String base = request.getServletContext().getRealPath("/upload");
        try {
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            String path= UUID.randomUUID().toString()+ext;
            System.out.println(base+"/"+path);
            File newFile=new File(base+"/"+path);
            file.transferTo(newFile);
            return  MapControll.getInstance().success().add("data",path).getMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  MapControll.getInstance().error().getMap();
    }



    @PostMapping("/audit")
    @ResponseBody
    public Map<String,Object> audit(@RequestBody Request request,HttpSession session){
        Object o = session.getAttribute("user");
        if(o instanceof Teacher){
            request.setStatus(Request.StatusType.status_1.getVal());
        }
        if(o instanceof User){
            request.setStatus(Request.StatusType.status_2.getVal());
        }
        List<Request> list = requestService.query(request);
        list.forEach(entity ->{
            if(entity.getStatus() == 1){
                entity.setStatusTxt(Request.StatusType.status_1.getMsg());
            }
            if(entity.getStatus() == 2){
                entity.setStatusTxt(Request.StatusType.status_2.getMsg());
            }
            if(entity.getStatus() == 3){
                entity.setStatusTxt(Request.StatusType.status_3.getMsg());
            }
            if(entity.getStatus() == 4){
                entity.setStatusTxt(Request.StatusType.status_4.getMsg());
            }
        });

        Integer count = requestService.count(request);
        return MapControll.getInstance().success().page(list,count).getMap();
    }

    @GetMapping("/audit")
    public String audit(){
        return AUDIT;
    }

    @GetMapping("/audit_add")
    public String audit_add(Integer id,ModelMap modelMap){
        modelMap.put("id",id);
        return "request/audit_add";
    }

    @PostMapping("/audit_add")
    @ResponseBody
    public Map<String,Object> audit_add(Integer id,Integer status,String type,String result){
        if("2".equals(type)){//teacher do...
            if(status == 3){
                status = 2;//赋值为2
            }
            Request request = new Request();
            request.setId(id);
            request.setStatus(status);
            request.setResult1(result);
            requestService.update(request);
            return MapControll.getInstance().success().getMap();
        }
        if("1".equals(type)){ //users...
            Request request = new Request();
            request.setId(id);
            request.setStatus(status);
            request.setResult2(result);
            requestService.update(request);
            return MapControll.getInstance().success().getMap();
        }
        return MapControll.getInstance().error().getMap();
    }





}
