package com.pkwiatko.domain;

import java.io.Serializable;

public class Plan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String student;
	private int dayNo;
	private int lessonNo;
	private String startTime;
	private String endTime;
	private String subjectName;
	private String teacherName;
	private String classroom;

	
	public Plan() {

	}


	public Plan(int id, String student, int dayNo, int lessonNo,
			String startTime, String endTime, String subjectName,
			String teacherName, String classroom) {
		super();
		this.id = id;
		this.student = student;
		this.dayNo = dayNo;
		this.lessonNo = lessonNo;
		this.startTime = startTime;
		this.endTime = endTime;
		this.subjectName = subjectName;
		this.teacherName = teacherName;
		this.classroom = classroom;
	}


	public Plan(String student, int dayNo, int lessonNo, String startTime,
			String endTime, String subjectName, String teacherName,
			String classroom) {
		super();
		this.student = student;
		this.dayNo = dayNo;
		this.lessonNo = lessonNo;
		this.startTime = startTime;
		this.endTime = endTime;
		this.subjectName = subjectName;
		this.teacherName = teacherName;
		this.classroom = classroom;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStudent() {
		return student;
	}


	public void setStudent(String student) {
		this.student = student;
	}


	public int getDayNo() {
		return dayNo;
	}


	public void setDayNo(int dayNo) {
		this.dayNo = dayNo;
	}


	public int getLessonNo() {
		return lessonNo;
	}


	public void setLessonNo(int lessonNo) {
		this.lessonNo = lessonNo;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public String getTeacherName() {
		return teacherName;
	}


	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public String getClassroom() {
		return classroom;
	}


	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Plan [id=" + id + ", student=" + student + ", dayNo=" + dayNo
				+ ", lessonNo=" + lessonNo + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", subjectName=" + subjectName
				+ ", teacherName=" + teacherName + ", classroom=" + classroom
				+ "]";
	}
	
	

	}
