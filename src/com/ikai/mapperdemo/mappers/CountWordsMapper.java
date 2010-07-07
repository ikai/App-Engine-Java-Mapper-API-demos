package com.ikai.mapperdemo.mappers;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * 
 * This mapper counts the number of total words across all comments. It cheats a
 * bit by just splitting on whitespace and just using the length. This mapper
 * demonstrates use of counters as well as using a completion callback.
 * 
 * @author Ikai Lan
 * 
 */
public class CountWordsMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger.getLogger(CountWordsMapper.class
			.getName());

	/*
	 * This is a bit of a lazy implementation more to prove a point than to
	 * actually be correct. Split on whitespace, count words
	 */
	@Override
	public void map(Key key, Entity value, Context context) {

		String comment = (String) value.getProperty("comment");
		if (comment != null) {
			String[] words = comment.split("\\s+");
			int wordCount = words.length;

			// Takes a "group" and a "counter"
			// We'll use these later to store the final count back in the
			// datastore
			context.getCounter("CommentWords", "count").increment(wordCount);
		}

	}
}