package com.zqy.dao;

import java.util.List;
import java.util.Map;

import com.zqy.entity.Job;

public interface JobDao {
    public int create(Job pi);

    public int delete(Map<String, Object> paramMap);

    public int update(Map<String, Object> paramMap);

    public List<Job> query(Map<String, Object> paramMap);

    public Job detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);
}