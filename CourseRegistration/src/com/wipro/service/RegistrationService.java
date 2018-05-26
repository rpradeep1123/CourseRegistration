package com.wipro.service;

import com.wipro.userbean.RegisterBean;

public interface RegistrationService {
	public String validateRequest(RegisterBean regBean);
	public String checkCourseAvailability(RegisterBean regBean);
	public String registerCourse(RegisterBean regBean);
	public boolean isRegistered(String userID, String courseID);
	public String cancelRegistration(String userID, String courseID);
	public String updateCourseID(String userID, String courseID);	
}
