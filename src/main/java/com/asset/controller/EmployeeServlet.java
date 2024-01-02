package com.asset.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.dao.EmployeeDao;
import com.asset.model.Employee;
import com.asset.model.Vendor;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDao employeeDao;
	private PrintWriter out;
	
	public void init() {
		employeeDao = new EmployeeDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		
		String employee_id = null;
		employee_id = request.getParameter("employee_id");
		
		if(employee_id != null) {
			try {
				int id = Integer.parseInt(employee_id);
				jsonArray = employeeDao.getAmountSpentForEmployee(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				jsonArray = employeeDao.getAllEmployees();
				
				out.println(jsonArray);
				out.flush();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Employee employee = new Employee();
		int result = 0;	
		JSONObject json = new JSONObject();
		try {
			String name = request.getParameter("name");
			employee.setName(name);
			result = employeeDao.createEmployee(employee);
			if (result == 1) {
				json.put("message", "successful");
			} else {
				json.put("message", "error");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		response.setContentType("application/json");
		out = response.getWriter();
		out.println(json);
		out.flush();
	}

}
