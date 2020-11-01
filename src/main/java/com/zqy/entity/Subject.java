package com.zqy.entity;

import com.zqy.utils.Entity;

/**
 * 
 * @author 596183363@qq.com
 * @time 2020-06-19 10:28:13
 */
public class Subject extends Entity {

	/**
	 * 
	 */
	private String college;
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String subjectName;

	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}