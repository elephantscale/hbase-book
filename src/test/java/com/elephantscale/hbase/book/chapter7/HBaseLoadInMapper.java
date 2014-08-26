package com.elephantscale.hbase.book.chapter7;

import java.io.IOException;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.NullOutputFormat;
import org.apache.hadoop.util.*;

public class HBaseLoadInMapper extends Configured implements Tool {

    private static final int KEY_LENGTH = 12;

    // Inner-class for map 
    static class FileLoadMapper<K, V> extends MapReduceBase implements
            Mapper<LongWritable, Text, K, V> {

        private HTable table;

        @Override
        public void map(LongWritable key, Text value,
                OutputCollector<K, V> output, Reporter reporter)
                throws IOException {
            if (isValidLine(value.toString())) {
                byte[] rowKey = makeRowKey(getFields(value.toString()));
                Put p = new Put(rowKey);
                String family = "fam1";
                String columnName = "col1";
                String columnValue = "val1";
                p.add(Bytes.toBytes(family), Bytes.toBytes(columnName), Bytes.toBytes(columnValue));
                table.put(p);
            }
        }

        @Override
        public void configure(JobConf jc) {
            // super.configure(jc);
            // Create the HBase table client once up-front and keep it around
            // rather than create on each map invocation.
            try {
                Configuration hBaseConfig = HBaseConfiguration.create();               
//                hBaseConfig.set("hbase.zookeeper.quorum", jc.get("ZooKeeper-Quorum"));
                table = new HTable(hBaseConfig, "mytable");

            } catch (IOException e) {
                throw new RuntimeException("Failed HTable construction", e);
            }
        }

        @Override
        public void close() throws IOException {
            super.close();
            table.close();
        }
    }

    @Override
    public int run(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: HBaseFileImporter <zookeeper-quorum> <input>");
            return -1;
        }
        JobConf conf = new JobConf(getConf(), HBaseLoadInMapper.class);
        conf.set("ZooKeeper-Quorum", args[0]);
        FileInputFormat.addInputPath(conf, new Path(args[1]));
        conf.setMapperClass(HBaseLoadInMapper.FileLoadMapper.class);
        conf.setNumReduceTasks(1);
        conf.setOutputFormat(NullOutputFormat.class);        
        JobClient.runJob(conf);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Running HBaseLoad from " + args[1]);
        int exitCode = ToolRunner.run(HBaseConfiguration.create(),
                new HBaseLoadInMapper(), args);
    }

    private static String[] getFields(String string) {
        return string.split(" ");
    }

    private static boolean isValidLine(String string) {
        return true;
    }

    /**
     * @return A row key composed of fields
     */
    public static byte[] makeRowKey(String[] fields) {
        byte[] row = new byte[KEY_LENGTH + Bytes.SIZEOF_LONG];
        Bytes.putBytes(row, 0, Bytes.toBytes(fields[0]), 0, KEY_LENGTH);
        Date time = new Date();
        long reverseOrderEpoch = Long.MAX_VALUE - time.getTime();
        Bytes.putLong(row, KEY_LENGTH, reverseOrderEpoch);
        return row;
    }
}
