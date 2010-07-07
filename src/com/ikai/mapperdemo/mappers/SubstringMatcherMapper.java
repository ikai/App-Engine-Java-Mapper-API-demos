package com.ikai.mapperdemo.mappers;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * 
 * This Mapper takes some input and counts the number of Comments which
 * contain that substring.
 * 
 * @author Ikai Lan
 * 
 */
public class SubstringMatcherMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger.getLogger(SubstringMatcherMapper.class
			.getName());

	/*
	 * Get the target that we want to match on and count the number of Comments that
	 * match it
	 */
	@Override
	public void map(Key key, Entity value, Context context) {

	    String substringToMatch = context.getConfiguration().get("mapreduce.mapper.counter.substringtarget");

		String comment = (String) value.getProperty("comment");
		if (comment != null) {
			if(comment.contains(substringToMatch)) {
				log.info("Found match in: " + comment);
				context.getCounter("SubstringMatch", "count").increment(1);				
			}	
		}

	}
}