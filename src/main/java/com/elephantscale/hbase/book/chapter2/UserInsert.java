package com.elephantscale.hbase.book.chapter2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


/**
 * before running this, create '<yourname>_users' table
 * (replace <yourname> with your username)
 * in hbase shell:
 *      create '<yourname>_users', 'info'
 */
public class UserInsert
{

    // TODO : update the table name with your username
    //static String tableName = "<your_name>_users";
    static String tableName = "mark_users";
    static String familyName = "info";

    public static void main(String[] args) throws Exception
    {
        Configuration config = HBaseConfiguration.create();
        try (HTable htable = new HTable(config, tableName)) {
            int total = 100;
            long t1 = System.currentTimeMillis();
            for (int i=0; i < total ; i++)
            {
                int userid = i;
                String email = "user-" + i + "@foo.com";
                String phone = "555-1234";

                byte [] key = Bytes.toBytes(userid);
                Put put = new Put (key);
                
                put.add(Bytes.toBytes(familyName), Bytes.toBytes("email"), Bytes.toBytes(email));  // <-- email goes here
                put.add(Bytes.toBytes(familyName), Bytes.toBytes("phone"), Bytes.toBytes(phone));  // <-- phone goes here
                htable.put(put);

            }
            long t2 = System.currentTimeMillis();
            System.out.println ("inserted " + total + " users  in " + (t2-t1) + " ms");
        }

    }
}