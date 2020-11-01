package com.zqy.service;

import com.github.pagehelper.PageHelper;
import com.zqy.dao.CourseDao;
import com.zqy.entity.Course;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public int create(Course pi) {
        return courseDao.create(pi);
    }

    public int delete(Integer id) {
        return courseDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = courseDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(Course course) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(course)).addId(course.getId()).getMap();
        return courseDao.update(map);
    }

    public List<Course> query(Course course) {
        if(course != null && course.getPage() != null){
            PageHelper.startPage(course.getPage(),course.getLimit());
        }
        return courseDao.query(BeanMapUtils.beanToMap(course));
    }

    public Course detail(Integer id) {
        return courseDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Course course) {
        return courseDao.count(BeanMapUtils.beanToMap(course));
    }

}
