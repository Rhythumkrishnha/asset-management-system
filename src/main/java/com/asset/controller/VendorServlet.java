package com.asset.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.dao.VendorDao;
import com.asset.model.Vendor;

/**
 * Servlet implementation class VendorServlet
 */
public class VendorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VendorDao vendorDao;
	private PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
    	vendorDao = new VendorDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		
		String vendor_id = null;
		vendor_id = request.getParameter("vendor_id");
		
		if (vendor_id != null) {
			try {
				int id = Integer.parseInt(vendor_id);
				jsonArray = vendorDao.getAmountSpentOnVendor(id);
				
				out.println(jsonArray);
				out.flush();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				jsonArray = vendorDao.getAllVendors();
				
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		
		Vendor vendor = new Vendor();
		vendor.setId(id);
		vendor.setName(name);
		
		int result = 0;	
		JSONObject json = new JSONObject();
		try {
			result = vendorDao.createVendor(vendor);
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
