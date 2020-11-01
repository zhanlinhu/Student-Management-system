package com.zqy.service;

import com.zqy.dao.UserDao;
import com.zqy.entity.User;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MD5Utils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int create(User pi) {
        pi.setUserPwd(MD5Utils.getMD5(pi.getUserPwd()));
        return userDao.create(pi);
    }

    public int delete(Integer id) {
        return userDao.delete(MapParameter.getInstance().addId(id).getMap());
    }
    public int delete(String ids) {
        int flag = 0;
        for (String str : ids.split(",")) {
            flag = userDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return flag;
    }

    public int update(User user) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(user)).addId(user.getId()).getMap();
        return userDao.update(map);
    }

    public List<User> query(User user) {
        return userDao.query(BeanMapUtils.beanToMap(user));
    }

    public User detail(Integer id) {
        return userDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(User user) {
        return userDao.count(BeanMapUtils.beanToMap(user));
    }

    public User login(String userName,String password){
        Map<String, Object> map = MapParameter.getInstance()
                .add("userName", userName)
                .add("userPwd", password)
                .getMap();
        return userDao.detail(map);
    }

}
