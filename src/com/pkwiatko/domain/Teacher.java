package com.pkwiatko.domain;

import java.io.Serializable;

public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String teacherName;
	

	public Teacher(int id, String teacherName) {
		super();
		this.id = id;
		this.teacherName = teacherName;
	
	}

	public Teacher(String teacherName) {
		super();
		this.teacherName = teacherName;
	
	}

	public Teacher() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", teacherName=" + teacherName
				+ "]";
	}

}
