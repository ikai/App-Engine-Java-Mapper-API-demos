package com.ikai.mapperdemo.mappers;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

public class NaiveToLowercaseMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger
			.getLogger(NaiveToLowercaseMapper.class.getName());

	private DatastoreService datastore;

	@Override
	public void taskSetup(Context context) {
		this.datastore = DatastoreServiceFactory.getDatastoreService();
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