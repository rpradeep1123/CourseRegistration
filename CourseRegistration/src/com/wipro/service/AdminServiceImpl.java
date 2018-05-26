package com.wipro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wipro.userbean.CourseBean;
import com.wipro.util.DBUtil;
import com.wipro.util.InvalidCourseDayException;
import com.wipro.util.InvalidCourseIDException;
import com.wipro.util.InvalidCourseNameException;

public class AdminServiceImpl implements AdminService {

	Connection conn=DBUtil.getDBConnection();
	
	@Override
	public String createCourse(CourseBean cbean) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "insert into CourseTable(CourseName,CourseID,Days)values(?,?,?)";
		if(this.validateRequest(cbean).equals("SUCCESS"))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, cbean.getCourseName());
				ps.setString(2, cbean.getCourseID());
				ps.setInt(3, cbean.getDays());
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result="New Course added : "+cbean.getCourseName();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result=this.validateRequest(cbean);
		}
		return result;
	}

	@Override
	public boolean isCourseAvailable(String courseID) {
		// TODO Auto-generated method stub
		Boolean result=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from CourseTable where CourseID=?";
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, courseID);
				rs=ps.executeQuery();
				while(rs.next())
				{
					if(rs.getInt(1)>0)
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
	public String updateCourseName(String courseID, String name) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "update CourseTable set CourseName=? where CourseID=?";
		if(this.isCourseAvailable(courseID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, courseID);
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result="Course Updated Successfully,new course name:"+name;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="Course Not Available, update failed";
		}
		return result;
	}

	@Override
	public String deleteCourse(String courseID) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		int rs = 0;
		String query = "delete from CourseTable where CourseID=?";
		if(this.isCourseAvailable(courseID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, courseID);
				rs=ps.executeUpdate();
				if(rs!=0)
				{
					result="Course Deleted : "+	courseID;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="Course deletion failed";
		}
		return result;
	}

	@Override
	public String countEntries(String courseID) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from CourseTable where CourseID=?";
		if(this.isCourseAvailable(courseID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, courseID);
				rs=ps.executeQuery();
				while(rs.next())
				{
					result=courseID+ " :  "+rs.getInt(1) +" registrants";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="Course not found";
		}
		return result;
	}

	@Override
	public Boolean isRegisteredUser(String userID) {
		// TODO Auto-generated method stub
		Boolean result=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from RegistrationTable where userId=?";
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, userID);
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
	public String countEntries(String userID, int value) {
		// TODO Auto-generated method stub
		String result=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select count(*) from RegistrationTable where UserId=?";
		if(this.isRegisteredUser(userID))
		{
			try {
				ps=conn.prepareStatement(query);
				ps.setString(1, userID);
				rs=ps.executeQuery();
				while(rs.next())
				{
					result=userID+ " :  "+rs.getInt(1) +" courses";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			result="User not found";
		}
		return result;
	}

	@Override
	public ArrayList<CourseBean> getAllCourses() {
		// TODO Auto-generated method stub
		ArrayList<CourseBean> courseList=new ArrayList<CourseBean>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from CourseTable";
		int count=0;
		try {
			ps=conn.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next())
			{
				CourseBean b=new CourseBean();
				b.setCourseID(rs.getString(1));
				b.setCourseName(rs.getString(2));
				b.setDays(rs.getInt(3));
				courseList.add(b);
				count++;
			}
			if(count==0)
			{
				courseList=null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;
	}

	@Override
	public void printCourseName() {
		// TODO Auto-generated method stub
		if(this.getAllCourses()!=null)
		{
			ArrayList<CourseBean> courseList =new ArrayList<CourseBean>();
			courseList=this.getAllCourses();
			for(int i=0;i<courseList.size();i++)
			{
				System.out.println("Course Name : "+courseList.get(i).getCourseName());
			}
		}else
		{
			System.out.println("No Records Found");
		}
	}

	@Override
	public void printCourseID() {
		// TODO Auto-generated method stub
		if(this.getAllCourses()!=null)
		{
			ArrayList<CourseBean> courseList =new ArrayList<CourseBean>();
			courseList=this.getAllCourses();
			for(int i=0;i<courseList.size();i++)
			{
				System.out.println("Course ID : "+courseList.get(i).getCourseID());
			}
		}else
		{
			System.out.println("No Records Found");
		}
	}

	@Override
	public void maxDayCourse() {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select MAX(Days),CourseID from CourseTable";
		try {
			ps=conn.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println("Course ID : "+rs.getInt(1));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void minDayCourse() {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select MIN(Days),CourseID from CourseTable";
		try {
			ps=conn.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println("Course ID : "+rs.getInt(1));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String validateRequest(CourseBean courseBean) {
		// TODO Auto-generated method stub
		String result=null;
		try {
			if(courseBean.getCourseID().length()!= 6 || !courseBean.getCourseID().startsWith("CS")||courseBean.getCourseID().isEmpty())
			{
				throw new InvalidCourseIDException();
			}else if(courseBean.getCourseName().length()<2 ||courseBean.getCourseName()==null|| courseBean.getCourseName().isEmpty())
			{
				throw new InvalidCourseNameException();
			}if(courseBean.getDays()<0)
			{
				throw new InvalidCourseDayException();
			}
			else
			{
				result="SUCCESS";
			}
		} catch (Exception e) {
			result=e.getMessage();
		} catch (InvalidCourseIDException e) {
			result=e.getMessage();
		} catch (InvalidCourseNameException e) {
			result=e.getMessage();
		} catch (InvalidCourseDayException e) {
			result=e.getMessage();
		}		
	return result;
	}

}
