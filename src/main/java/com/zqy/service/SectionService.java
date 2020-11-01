package com.zqy.service;

import com.zqy.dao.SectionDao;
import com.zqy.entity.Section;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectionService {

    @Autowired
    private SectionDao sectionDao;

    public int create(Section pi) {
        return sectionDao.create(pi);
    }

    public int delete(Integer id) {
        return sectionDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = sectionDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(Section section) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(section)).addId(section.getId()).getMap();
        return sectionDao.update(map);
    }

    public List<Section> query(Section section) {
        return sectionDao.query(BeanMapUtils.beanToMap(section));
    }

    public Section detail(Integer id) {
        return sectionDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Section section) {
        return sectionDao.count(BeanMapUtils.beanToMap(section));
    }

    public List<Section> queryByStudent(Integer studentId){
        Map<String, Object> map = MapParameter.getInstance().add("studentId", studentId).getMap();
        return sectionDao.queryByStudent(map);
    }

    public List<Section> queryByTeacher(Integer teacherId){
        Map<String, Object> map = MapParameter.getInstance().add("teacherId", teacherId).getMap();
        return sectionDao.queryByTeacher(map);
    }

}
