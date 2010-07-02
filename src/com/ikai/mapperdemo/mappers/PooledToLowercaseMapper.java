package com.ikai.mapperdemo.mappers;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

public class PooledToLowercaseMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger
			.getLogger(PooledToLowercaseMapper.class.getName());

	@Override
	public void map(Key key, Entity value, Context context) {
		log.warning("Mapping key: " + key);

		if (value.hasProperty("comment")) {
			String comment = (String) value.getProperty("comment");
			comment = comment.toLowerCase();
			value.setProperty("comment", comment);
			value.setProperty("updatedAt", new Date());

			DatastoreMutationPool mutationPool = this.getAppEngineContext(
					context).getMutationPool();
			mutationPool.put(value);
		}
	}
}