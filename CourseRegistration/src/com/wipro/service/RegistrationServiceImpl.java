package com.wipro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wipro.userbean.RegisterBean;
import com.wipro.util.DBUtil;
import com.wipro.util.InvalidCourseDayException;
import com.wipro.util.InvalidRegistrationException;
import com.wipro.util.InvalidUserIDException;

public class RegistrationServiceImpl implements RegistrationService{

	Connection conn=DBUtil.getDBConnection();
	@Override
	public String validateRequest(RegisterBean regBean) {
		// TODO Auto-generated method stub
		String result=null;
			try {
				if(regBean == null)
				{
					throw new InvalidRegistrationException();
				}else if(regBean.getUserID().length()!= 8 || !regBean.getUserID().startsWith("ST")||regBean.getUserID().isEmpty())
				{
					throw new InvalidUserIDException();
				}else if(regBean.getCourseIDToRegister().length()!=6 ||!regBean.getCourseIDToRegister().startsWith("CS") || regBean.getCourseIDToRegister().isEmpty())
				{
					throw new InvalidCourseDayException();
				}else
				{
					result="SUCCESS";
				}
			} catch (Exception e) {
				result=e.getMessage();
			} catch (InvalidRegistrationException e) {
				result=e.getMessage();
			} catch (InvalidUserIDException e) {
				result=e.getMessage();
			} catch (InvalidCourseDayException e) {
				result=e.getMessage();
			}		
		return result;
	}

	@Override
	public String checkCourseAvailability(RegisterBean regBean) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from CourseTable where CourseID=?";
		if(this.validateRequest(regBean).equals("SUCCESS"))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, regBean.getCourseIDToRegister());
				rs=ps.executeQuery();
				while(rs.next())
				{
					if(rs.getInt(1)>0)
					{
						result="Available";
					}else
					{
						result="Course Not Available";
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result=this.validateRequest(regBean);
		}
		return result;
	}

	@Override
	public String registerCourse(RegisterBean regBean) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "insert into RegistrationTable(UserID,StudentName,CourseRegistered)values(?,?,?)";
		if(this.checkCourseAvailability(regBean).equals("Available"))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, regBean.getUserID());
				ps.setString(2, regBean.getStudentName());
				ps.setString(3, regBean.getCourseIDToRegister());
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result="Registration Successful";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result=this.checkCourseAvailability(regBean);
		}
		return result;
	}

	@Override
	public boolean isRegistered(String userID, String courseID) {
		// TODO Auto-generated method stub
		Boolean result=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from RegistrationTable where userId=? and courseId=?";
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, userID);
				ps.setString(2, courseID);
				rs=ps.executeQuery();
				while(rs.next())
				{
					if(rs.getInt(1)!=0)
					{
						result=true;
					}else
					{
						result=false;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public String cancelRegistration(String userID, String courseID) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "delete from RegistrationTable where UserID=? and CourseRegistered=?";
		if(this.isRegistered(userID, courseID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, userID);
				ps.setString(2, courseID);
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result=courseID+" registration canceled for the user"+	userID;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="Your cancellation request failed, you are not the registered user for the course "+courseID;
		}
		return result;
	}

	@Override
	public String updateCourseID(String userID, String courseID) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "update RegistrationTable set CourseRegistered=? where UserID=?";
		if(this.isRegistered(userID, courseID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, courseID);
				ps.setString(2, userID);
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result="Course Updated Successfully,new course :"+courseID;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="Your update request failed, you are not the registered user for the course "+courseID;
		}
		return result;
	}

}
