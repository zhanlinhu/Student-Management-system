package com.zqy.service;

import com.github.pagehelper.PageHelper;
import com.zqy.dao.ClazzDao;
import com.zqy.entity.Clazz;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzService {


//    @Autowired 是一个注释，
//    它可以对类成员变量、方法及构造函数进行标注，
//    让 spring 完成 bean 自动装配的工作。例如我们测试时候要new ClassPathXmlApplicationContext("xx.xml");
//    还需要getbean()
    @Autowired
    private ClazzDao clazzDao;



    public int create(Clazz pi) {
        return clazzDao.create(pi);
    }

    public int delete(Integer id) {
        return clazzDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = clazzDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(Clazz clazz) {
        return clazzDao.update(MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(clazz)).addId(clazz.getId()).getMap());
    }

    public List<Clazz> query(Clazz clazz) {
        if(clazz != null && clazz.getPage() != null){
            PageHelper.startPage(clazz.getPage(),clazz.getLimit());
        }
        return clazzDao.query(BeanMapUtils.beanToMap(clazz));
    }

    public Clazz detail(Integer id) {
        return clazzDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Clazz clazz) {
        return clazzDao.count(BeanMapUtils.beanToMap(clazz));
    }

}
