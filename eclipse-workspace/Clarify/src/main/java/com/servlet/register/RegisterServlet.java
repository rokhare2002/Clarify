package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	//creating the query
	private static final String INSERT_QUERY ="INSERT INTO details" + "(name,city,mobile,dob)" + "VALUES(?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set Content type
		res.setContentType("text/html");
		//reads the input from users
		String name = req.getParameter("name");
		String city = req.getParameter("city");
		String mobile = req.getParameter("mobile");
		String dob = req.getParameter("dob");
		
		//loading of jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//connecting the MySQL
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///user","rohit","data@123");
				PreparedStatement ps = con.prepareStatement(INSERT_QUERY);){
			//set the values
			ps.setString(1, name);
			ps.setString(2, city);
			ps.setString(3, mobile);
			ps.setString(4, dob);
			
			//execute the query
			int count = ps.executeUpdate();
			
			if(count==0) {
				pw.println("Registration not complete.");
			}else {
				pw.println("Registration complete.");
			}	//specifies any errors
		}catch(SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
		}catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
		
		//close the stream
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}