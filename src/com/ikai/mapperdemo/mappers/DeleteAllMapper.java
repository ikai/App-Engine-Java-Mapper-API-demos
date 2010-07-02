package com.ikai.mapperdemo.mappers;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * 
 * This Mapper deletes all Entities of a given kind. It simulates the
 * DROP TABLE functionality asked for by developers.
 * 
 * @author Ikai Lan
 *
 */
public class DeleteAllMapper extends
		AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
	private static final Logger log = Logger.getLogger(DeleteAllMapper.class
			.getName());

	@Override
	public void map(Key key, Entity value, Context context) {
		log.info("Adding key to deletion pool: " + key);
		DatastoreMutationPool mutationPool = this.getAppEngineContext(context)
				.getMutationPool();
		mutationPool.delete(value.getKey());
	}
}