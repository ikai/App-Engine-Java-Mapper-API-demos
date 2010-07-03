package com.ikai.mapperdemo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class ViewCommentsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>Comments</h1>");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		displayTotalWordCount(out, datastore);
		displayComments(out, datastore);
	}

	private void displayComments(PrintWriter out, DatastoreService datastore) {
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
		for (Entity comment : datastore.prepare(query).asIterable()) {
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

	private void displayTotalWordCount(PrintWriter out,
			DatastoreService datastore) {
		Key totalWordCountKey = KeyFactory.createKey("TotalWordCount",
				"total_word_count");
		Entity totalWordCountEntity;
		try {
			totalWordCountEntity = datastore.get(totalWordCountKey);
			Long count = (Long) totalWordCountEntity.getProperty("count");
			Date lastUpdated = (Date) totalWordCountEntity
					.getProperty("updatedAt");

			out.println("<div>");
			out.println("<p>");
			out.println("<strong>Total Words in All Comments: </strong>"
					+ count);
			out.println("</p>");

			out.println("<p>");
			out.println("<strong>Last Updated: </strong>" + lastUpdated);
			out.println("</p>");

			out.println("</div>");

		} catch (EntityNotFoundException e) {
			out
					.println("</h2>TotalWordCount Map not run or not completed yet.</h2>");
		}
	}
}
