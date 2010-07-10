package com.ikai.mapperdemo.mappers;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * 
 * The functionality of this is exactly the same as in {@link NaiveToLowercaseMapper}.
 * The advantage here is that since a {@link DatastoreMutationPool} is used, mutations
 * can be done in batch, saving API calls.
 * 
 * @author Ikai Lan
 *
 */
public class PooledToLowercaseMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger
			.getLogger(PooledToLowercaseMapper.class.getName());

	@Override
	public void map(Key key, Entity value, Context context) {
		log.info("Mapping key: " + key);

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