package com.ikai.mapperdemo.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SuccessfulUploadServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String blobKey = req.getParameter("blob-key");

		resp.setContentType("text/html");
		resp.getWriter().println("Successfully uploaded. Download file: <br/>");
		resp.getWriter().println(
				"<a href='/serve?blob-key=" + blobKey
						+ "'>Click to download</a>");
	}

}
