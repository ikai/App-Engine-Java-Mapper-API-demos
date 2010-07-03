package com.ikai.mapperdemo.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.JobID;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.mapreduce.MapReduceState;
import com.ikai.mapperdemo.mappers.CountWordsMapper;

/**
 * This is the servlet that takes care of any processing we have to do after we
 * have finished running {@link CountWordsMapper}.
 * 
 * This is just a standard servlet - we can do anything we want here. We can use
 * any App Engine API such as email or XMPP, for instance, to notify an
 * administrator. We could also store a final state into the datastore - in
 * fact, that is what this example below does.
 * 
 * @author Ikai Lan
 * 
 */
@SuppressWarnings("serial")
public class WordCountCompletedCallbackServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String jobIdName = req.getParameter("job_id");
		JobID jobId = JobID.forName(jobIdName);

		// A future iteration of this will likely contain a default
		// option if we don't care which DatastoreService instance we use.
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {

			// We get the state back from the job_id parameter. The state is
			// serialized and stored in the datastore, so we pass an instance
			// of the datastore service.
			MapReduceState mrState = MapReduceState.getMapReduceStateFromJobID(
					datastore, jobId);

			// There's a bit of ceremony to get the actual counter. This
			// example is intentionally verbose for clarity. First get all the
			// Counters,
			// then we get the CounterGroup, then we get the Counter, then we
			// get the count.
			Counters counters = mrState.getCounters();
			CounterGroup counterGroup = counters.getGroup("CommentWords");
			Counter counter = counterGroup.findCounter("count");
			long wordCount = counter.getValue(); // Finally!

			// Let's create a special datastore Entity for this value so
			// we can reference it on the ViewComments page
			Entity totalCountEntity = new Entity("TotalWordCount",
					"total_word_count");
			totalCountEntity.setProperty("count", wordCount);
			
			// Now we timestamp this bad boy
			totalCountEntity.setProperty("updatedAt", new Date()); 
			datastore.put(totalCountEntity);

		} catch (EntityNotFoundException e) {
			throw new IOException("No datastore state");
		}

	}

}
