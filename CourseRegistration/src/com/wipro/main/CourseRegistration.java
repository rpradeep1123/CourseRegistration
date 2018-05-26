package com.wipro.main;

import java.util.Scanner;

import com.wipro.service.AdminServiceImpl;
import com.wipro.service.RegistrationServiceImpl;
import com.wipro.userbean.CourseBean;
import com.wipro.userbean.RegisterBean;

public class CourseRegistration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegistrationServiceImpl reg = new RegistrationServiceImpl();
		AdminServiceImpl adm = new AdminServiceImpl();

		CourseBean cbean = new CourseBean();
		RegisterBean rbean=new RegisterBean();

		Scanner sc = new Scanner(System.in);

		System.out
				.println("Please choose action from below to execute \n 1.Admin Activities \n 2. Course Activities \n\n Enter the option and press ENTER");
		switch (Integer.parseInt(sc.next())) {
		case 1:
			System.out
					.println("Please choose any admin activity \n 1.Validate Course \n 2. Add new course \n 3.Check Course availability \n 4. Update course details \n 5.Delete Course \n 6.Print Course name \n 7.Print Course IDs \n 8.Show Maimum days course \n 9.Show Minimum days course");
			switch(Integer.parseInt(sc.next()))
			{
			case 1:
				System.out.println("Enter Course ID to validate");
				cbean.setCourseID(sc.next());
				System.out.println("Enter Course name to validate");
				cbean.setCourseName(sc.next());
				System.out.println("Enter Course Days to validate");
				cbean.setDays(Integer.parseInt(sc.next()));
				System.out.println("Validation : "+adm.validateRequest(cbean));
				break;
			case 2:
				System.out.println("Enter Course ID to add");
				cbean.setCourseID(sc.next());
				System.out.println("Enter Course name to add");
				cbean.setCourseName(sc.next());
				System.out.println("Enter Course Days to add");
				cbean.setDays(Integer.parseInt(sc.next()));
				System.out.println(adm.createCourse(cbean));
				break;
			case 3:
				System.out.println("Enter Course ID to check");
				System.out.println(adm.isCourseAvailable(sc.next()));
				break;
			case 4:
				System.out.println("Enter course id to update");
				String courseId=sc.next();
				System.out.println("Enter course name to update");
				String courseName=sc.next();
				System.out.println(adm.updateCourseName(courseId,courseName));
				break;
			case 5:
				System.out.println("Enter course id to delete");
				System.out.println(adm.deleteCourse(sc.next()));
				break;
			case 6:
				adm.printCourseName();
				break;
			case 7:
				adm.printCourseID();
				break;
			case 8:
				adm.maxDayCourse();
				break;
			case 9:
				adm.minDayCourse();
				break;
			}
			break;
		case 2:
			System.out.println("Please choose any course activity \n 1.Validate User details \n 2.Check Course Availability \n 3.Register new course \n 4.Check whether course already registered \n 5.Cancel Course \n 6.Update course details");
			switch(Integer.parseInt(sc.next()))
			{
			case 1:
				System.out.println("Enter Userid to validate");
				rbean.setUserID(sc.next());
				System.out.println("Enter Course ID to validate");
				rbean.setCourseIDToRegister(sc.next());
				System.out.println(reg.validateRequest(rbean));
				break;
			case 2:
				System.out.println("Enter course id to check ");
				rbean.setCourseIDToRegister(sc.next());
				System.out.println(reg.checkCourseAvailability(rbean));
				break;
			case 3:
				System.out.println("Enter User ID to register");
				rbean.setUserID(sc.next());
				System.out.println("Enter Student nameto register");
				rbean.setStudentName(sc.next());
				System.out.println("Enter course ID to register");
				rbean.setCourseIDToRegister(sc.next());
				System.out.println(reg.registerCourse(rbean));
				break;
			case 4:
				System.out.println("Enter course id to check");
				String courseId=sc.next();
				System.out.println("Enter user ID to check");
				String userId=sc.next();
				System.out.println(reg.isRegistered(userId, courseId));
				break;
			case 5:
				System.out.println("Enter userid to cancel");
				String userID=sc.next();
				System.out.println("Enter course id to cancel");
				String courseID=sc.next();
				System.out.println(reg.cancelRegistration(userID, courseID));
				break;
			case 6:
				System.out.println("Enter userid to cancel");
				 userID=sc.next();
				System.out.println("Enter course id to cancel");
				 courseID=sc.next();
				System.out.println(reg.updateCourseID(userID, courseID));
				break;
			}
			break;
		}		
	}

}
