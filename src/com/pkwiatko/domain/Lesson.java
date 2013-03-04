package com.pkwiatko.domain;

import java.io.Serializable;

public class Lesson implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	private int lessonNo;
	private String startTime;
	private String endTime;
	
	public Lesson(){};
	
	public Lesson(int id, int lessonNo, String startTime, String endTime)	{
		this.id=id;
		this.lessonNo = lessonNo;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Lesson(int lessonNo, String startTime, String endTime)	{
		this.lessonNo = lessonNo;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Lesson [id=" + id + ", lessonNo=" + lessonNo + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
	

}
