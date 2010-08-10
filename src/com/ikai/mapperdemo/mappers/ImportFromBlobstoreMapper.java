package com.ikai.mapperdemo.mappers;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.BlobstoreRecordKey;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * 
 * This Mapper imports from a CSV file in the Blobstore.
 * 
 * @author Ikai Lan
 *
 */
public class ImportFromBlobstoreMapper extends
		AppEngineMapper<BlobstoreRecordKey, byte[], NullWritable, NullWritable> {
	private static final Logger log = Logger.getLogger(ImportFromBlobstoreMapper.class
			.getName());

	@Override
	public void map(BlobstoreRecordKey key, byte[] segment, Context context) {
		
		String line = new String(segment);
		
		log.info("At offset: " + key.getOffset());
		log.info("Got value: " + line);
		
		// Line format looks like this:
		// 10644,"US","VA","Tazewell","24651",37.0595,-81.5220,559,276
		// We're also assuming no errant commas in this simple example
		
		String[] values = line.split(",");
		String id = values[0];
		String state = values[2];
		String cityName = values[3];		
		String zipcode = values[4];
		Double latitude = Double.parseDouble(values[5]);
		Double longitude = Double.parseDouble(values[6]);		
		
		state = state.replaceAll("\"", "");
		cityName = cityName.replaceAll("\"", "");
		zipcode = zipcode.replaceAll("\"", "");
		
		if(!zipcode.isEmpty()) {
			Entity zip = new Entity("Zip", zipcode);
			zip.setProperty("state", state);
			zip.setProperty("city", cityName);
			zip.setProperty("latitude", latitude);
			zip.setProperty("longitute", longitude);
			
			Entity city = new Entity("City", cityName);
			city.setProperty("state", state);
			city.setUnindexedProperty("zip", zipcode);
			
			DatastoreMutationPool mutationPool = this.getAppEngineContext(context)
					.getMutationPool();
			mutationPool.put(zip);
			mutationPool.put(city);
		}

	}
}