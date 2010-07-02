package com.ikai.mapperdemo;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

public class Mapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger.getLogger(Mapper.class.getName());

	private DatastoreService datastore;

	public Mapper() {
	}

	@Override
	public void taskSetup(Context context) {

	}

	@Override
	public void taskCleanup(Context context) {
		log.warning("Doing per-task cleanup");
	}

	@Override
	public void setup(Context context) {
		this.datastore = DatastoreServiceFactory.getDatastoreService();
		log.warning("Doing per-worker setup");
	}

	@Override
	public void cleanup(Context context) {
		log.warning("Doing per-worker cleanup");
	}

	@Override
	  public void map(Key key, Entity value, Context context) {
	    log.warning("Mapping key: " + key);
	    	    
	    if (value.hasProperty("comment")) {
	    	String comment = (String) value.getProperty("comment");
	    	comment = comment.toLowerCase();
	    	value.setProperty("comment", comment);
	    	value.setProperty("updatedAt", new Date());
	    	
	    	datastore.put(value);
	    	
	    }
	  }
}