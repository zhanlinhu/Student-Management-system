package com.zqy.service;

import com.github.pagehelper.PageHelper;
import com.zqy.dao.TeacherDao;
import com.zqy.entity.Teacher;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MD5Utils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    public int create(Teacher pi) {
        pi.setTeacherPwd(MD5Utils.getMD5(pi.getTeacherPwd()));
        return teacherDao.create(pi);
    }

    public int delete(Integer id) {
        return teacherDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = teacherDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(Teacher teacher) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(teacher)).addId(teacher.getId()).getMap();
        return teacherDao.update(map);
    }

    public List<Teacher> query(Teacher teacher) {
        if(teacher != null && teacher.getPage() != null){
            PageHelper.startPage(teacher.getPage(),teacher.getLimit());
        }
        return teacherDao.query(BeanMapUtils.beanToMap(teacher));
    }

    public Teacher detail(Integer id) {
        return teacherDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Teacher teacher) {
        return teacherDao.count(BeanMapUtils.beanToMap(teacher));
    }

    public Teacher login(String userName, String password){
        Map<String, Object> map = MapParameter.getInstance()
                .add("teacherName", userName)
                .add("teacherPwd", password)
                .getMap();
        return teacherDao.detail(map);
    }


}
