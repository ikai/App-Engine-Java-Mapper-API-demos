package com.ikai.mapperdemo.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class AddCommentServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// resp.setContentType("text/plain");
		// resp.getWriter().println("Hello, world");
		String comment = req.getParameter("comment");
		comment = escapeCommentHtml(comment);
		
		Entity entry = new Entity("Comment");
		entry.setProperty("comment", comment);
		entry.setProperty("createdAt", new Date());
		entry.setProperty("locale", req.getLocale().toString());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		datastore.put(entry);
		
		resp.sendRedirect("/view");
	}
	
	/*
	 * A not-foolproof but cheap and dirty way to escape <> characters
	 * that lets me get enough security without having to import a lot
	 * of new packages. NOT FOOLPROOF.
	 */
	protected String escapeCommentHtml(String comment) {
		comment = comment.replaceAll("<", "&lt;");
		comment = comment.replaceAll(">", "&gt;");		
		return comment;
	}
}

