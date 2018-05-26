package com.wipro.service;

import java.util.ArrayList;

import com.wipro.userbean.CourseBean;

public interface AdminService {
	public String validateRequest(CourseBean courseBean);
	public String createCourse(CourseBean cbean);
	public boolean isCourseAvailable(String courseID);
	public String updateCourseName(String courseID, String name);
	public String deleteCourse(String courseID);
	public String countEntries(String courseID);
	public Boolean isRegisteredUser(String userID);
	public String countEntries(String userID, int value);
	public ArrayList<CourseBean> getAllCourses();
	public void printCourseName();
	public void printCourseID();
	public void maxDayCourse();
	public void minDayCourse();



}
