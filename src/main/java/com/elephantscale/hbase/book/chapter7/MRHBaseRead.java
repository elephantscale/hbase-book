package com.elephantscale.hbase.book.chapter7;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.util.Tool;

/**
 *
 * @author mark
 */
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;

public class MRHBaseRead extends Configured implements Tool {

    /**
     *
     * @param args
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    @Override
    public int run(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration config = HBaseConfiguration.create();
        Job job = new Job(config, "ExampleRead");

        job.setJarByClass(MRHBaseRead.class);     // class that contains mapper

        Scan scan = new Scan();

        scan.setCaching(
                500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
        scan.setCacheBlocks(
                false);  // don't set to true for MR jobs
        String tableName = "mytable";

        TableMapReduceUtil.initTableMapperJob(
                tableName, // input HBase table name
                scan, // Scan instance to control CF and attribute selection
                MyMapper.class, // mapper
                Text.class, // mapper output key
                Text.class, // mapper output value
                job);
        job.setOutputFormatClass(NullOutputFormat.class);   // because we aren't emitting anything from mapper

        boolean b = job.waitForCompletion(true);
        if (!b) {
            throw new IOException("error with job!");
        }
        return 0;
    }

    public static class MyMapper extends TableMapper<Text, Text> {

        @Override
        public void map(ImmutableBytesWritable row, Result value, Context context) throws InterruptedException, IOException {
            // process data for the row from the Result instance.
        }
    }
}