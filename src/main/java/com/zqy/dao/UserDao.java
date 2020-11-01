package com.zqy.dao;

import java.util.List;
import java.util.Map;

import com.zqy.entity.User;

public interface UserDao {
    public int create(User pi);

    public int delete(Map<String, Object> paramMap);

    public int update(Map<String, Object> paramMap);

    public List<User> query(Map<String, Object> paramMap);

    public User detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);
}