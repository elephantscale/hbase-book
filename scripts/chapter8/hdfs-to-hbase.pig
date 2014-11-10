-- ## pig script to load data from HDFS into HBase

data = LOAD 'hbase-import/' using PigStorage(',') as (sensor_id:chararray, max:int, min:int);
-- describe data;
-- dump data;

-- # first field in the tuple is row_key
-- # we are mapping others into their respective fields
store data into 'hbase://sensors' using org.apache.pig.backend.hadoop.hbase.HBaseStorage('f:max,f:min');
