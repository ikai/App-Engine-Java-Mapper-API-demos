package com.ikai.mapperdemo.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class ViewCommentsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>Comments here</h1>");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Comment");
		query.addSort("createdAt", SortDirection.DESCENDING);
		
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>ID</th>");	
		out.println("<th>comment</th>");
		out.println("<th>locale</th>");		
		out.println("<th>created</th>");
		out.println("<th>updated</th>");	
		out.println("</tr>");		
		for(Entity comment : datastore.prepare(query).asIterable()) {
			out.println("<tr>");
			
			out.println("<td>" + comment.getKey() + "</td>");
			out.println("<td>" + comment.getProperty("comment") + "</td>");
			out.println("<td>" + comment.getProperty("locale") + "</td>");
			out.println("<td>" + comment.getProperty("createdAt") + "</td>");
			out.println("<td>" + comment.getProperty("updatedAt") + "</td>");
			
			out.println("</tr>");
		}
		out.println("</table>");		
	}
}
