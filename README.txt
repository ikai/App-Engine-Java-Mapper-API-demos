Demonstrates a bunch of useful Mappers for use with the App Engine Mapreduce library

The Mapper library can be found here:

http://code.google.com/p/appengine-mapreduce

This project includes sample code for using the Java version of this
library. Code samples include:

- Changing all Strings to lowercase across all Entities
- Changing all Strings to lowercase across all Entities using a mutation pool (more efficient)
- Deleting all Entities of a given Kind
- Counting all the words across all Entities, then using a callback URL to save them
- Taking an input in the Mapper interface
- Importing data from a CSV file into the datastore

The introductory blog post to the Mapper API can be found here:

http://ikaisays.com/2010/07/09/using-the-java-mapper-framework-for-app-engine/

A follow up post about reading input from the Blobstore can be found here:

http://ikaisays.com/2010/07/09/using-the-java-mapper-framework-for-app-engine/