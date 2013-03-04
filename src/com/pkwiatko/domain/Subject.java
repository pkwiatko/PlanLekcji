package com.pkwiatko.domain;

import java.io.Serializable;

public class Subject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String subjectName;
	

	public Subject(int id, String subjectName) {
		super();
		this.id = id;
		this.subjectName = subjectName;
	
	}

	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
	
	}

	public Subject() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", subjectName=" + subjectName
				+ "]";
	}

}
