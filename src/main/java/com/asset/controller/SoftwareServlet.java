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

import com.asset.model.Software;
import com.asset.dao.SoftwareDao;

/**
 * Servlet implementation class SoftwareServlet
 */
public class SoftwareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Software software = null;
	private SoftwareDao softwareDao = null;
	private PrintWriter out = null;
	
	public void init() {
		software = new Software();
		softwareDao = new SoftwareDao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareServlet() {
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
		
		JSONArray jsonArray = null;
		String software_id = null;
		software_id = request.getParameter("software_id");
		String vendor_id = null;
		vendor_id = request.getParameter("vendor_id");
		
		if (software_id != null) {
			try {
				int id = Integer.parseInt(software_id);
				jsonArray = softwareDao.getAmountSpentForSoftware(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if (vendor_id != null) {
			try {
				int id = Integer.parseInt(vendor_id);
				jsonArray = softwareDao.getNumberOfInstalltionOfSoftware(id);
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				jsonArray = softwareDao.getAllSoftwares();
				
				out.println(jsonArray);
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int vendor_id = Integer.parseInt(request.getParameter("vendor_id"));
		int cost = Integer.parseInt(request.getParameter("cost"));
		
		software.setName(name);
		software.setVendor_id(vendor_id);
		software.setCost(cost);
		
		int result = 0;
		JSONObject json = new JSONObject();
		
		try {
			result = softwareDao.createSoftware(software);
			if(result == 1) {
				json.put("message", "succesful");
			} else {
				json.put("message", "error");
			}
			
			out = response.getWriter();
			response.setContentType("application/json");
			out.println(json);
			out.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
