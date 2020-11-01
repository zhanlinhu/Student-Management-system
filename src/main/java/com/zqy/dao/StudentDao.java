package com.zqy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zqy.entity.Student;

public interface StudentDao {
    public int create(Student pi);

    public int delete(Map<String, Object> paramMap);

    public int update(Map<String, Object> paramMap);

    public List<Student> query(Map<String, Object> paramMap);

    public Student detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);

    public List<HashMap> querySelectStudent(Map<String, Object> paramMap);

    public List<Student> queryStudentByTeacher(Map<String, Object> paramMap);
}