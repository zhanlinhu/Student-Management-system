package com.zqy.service;

import com.github.pagehelper.PageHelper;
import com.zqy.dao.RequestDao;
import com.zqy.entity.Request;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    public int create(Request pi) {
        return requestDao.create(pi);
    }

    public int delete(Integer id) {
        return requestDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = requestDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(Request request) {
        return requestDao.update(MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(request)).addId(request.getId()).getMap());
    }

    public List<Request> query(Request request) {
        if(request != null && request.getPage() != null){
            PageHelper.startPage(request.getPage(),request.getLimit());
        }
        return requestDao.query(BeanMapUtils.beanToMap(request));
    }

    public Request detail(Integer id) {
        return requestDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Request request) {
        return requestDao.count(BeanMapUtils.beanToMap(request));
    }

}
