package com.asset.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.dao.DeviceDao;
import com.asset.model.Device;

/**
 * Servlet implementation class DeviceServlet
 */
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DeviceDao deviceDao;
	private Device device;
	private PrintWriter out;
	
	public void init() {
		try {
			deviceDao = new DeviceDao();
			device = new Device();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			JSONArray jsonArray = deviceDao.getAllDevices();
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonArray);
			out.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int result = 0;	
		JSONObject json = new JSONObject();
		try {
			int employee_id = Integer.parseInt(request.getParameter("employee_id"));
			String category = request.getParameter("category");
			String variant = request.getParameter("variant");
			
			device.setEmployee_id(employee_id);
			device.setCategory(category);
			device.setVariant(variant);
			
			result = deviceDao.createDevice(device);
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
