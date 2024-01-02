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

import com.asset.dao.LicenseDao;
import com.asset.model.License;

/**
 * Servlet implementation class LicenseServlet
 */
public class LicenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LicenseDao licenseDao;
	private License license;
	private PrintWriter out;
	
	public void init() {
		licenseDao = new LicenseDao();
		license = new License();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LicenseServlet() {
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
		
		String software_id = null;
		software_id = request.getParameter("software_id");
		String device_id = null;
		device_id = request.getParameter("device_id");
		String employee_id = null;
		employee_id = request.getParameter("employee_id");
		
		if(software_id != null) {
			try {
				JSONArray jsonArray = new JSONArray();
				int id = Integer.parseInt(software_id);
				jsonArray = licenseDao.getLicenseBySoftwareId(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(device_id != null) {
			try {
				JSONArray jsonArray = new JSONArray();
				int id = Integer.parseInt(device_id);
				jsonArray = licenseDao.getSoftwareInstalledOnDevice(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(employee_id != null) {
			try {
				JSONArray jsonArray = new JSONArray();
				int id = Integer.parseInt(employee_id);
				jsonArray = licenseDao.getSoftwareInstalledForEmployee(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else{
			try {
				JSONArray jsonArray = licenseDao.getAllLicenses();
				response.setContentType("application/json");
				out = response.getWriter();
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
		int result = 0;	
		JSONObject json = new JSONObject();
		try {
			int device_id = Integer.parseInt(request.getParameter("device_id"));
			int cost = Integer.parseInt(request.getParameter("cost"));
			int software_id = Integer.parseInt(request.getParameter("software_id"));
			
			license.setDevice_id(device_id);
			license.setCost(cost);
			license.setSoftware_id(software_id);
			
			result = licenseDao.createLicense(license);
			if (result == 1) {
				json.put("message", "successful");
			} else {
				json.put("message", "error");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		out.println(json);
		out.flush();
	}

}
