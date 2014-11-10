/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hbase_dp.ch8;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class UserInsert2 {

    // TODO : update the table name with your username
    static String tableName = "users";
    static String familyName = "info";

    public static void main(String[] args) throws Exception {
        Configuration config = HBaseConfiguration.create();
        // change the following to connect to remote clusters
        // config.set("hbase.zookeeper.quorum", "localhost");
        
        long t1a = System.currentTimeMillis();
        HConnection hConnection = HConnectionManager.createConnection(config);
        long t1b = System.currentTimeMillis();
        System.out.println ("Connection manager in : " + (t1b-t1a) + " ms");

        // simulate the first 'connection'
        long t2a = System.currentTimeMillis();
        HTableInterface htable = hConnection.getTable(tableName) ;
        long t2b = System.currentTimeMillis();
        System.out.println ("first connection in : " + (t2b-t2a) + " ms");
        
        // second connection
        long t3a = System.currentTimeMillis();
        HTableInterface htable2 = hConnection.getTable(tableName) ;
        long t3b = System.currentTimeMillis();
        System.out.println ("second connection : " + (t3b-t3a) + " ms");

        int total = 100;
        long t4a = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            int userid = i;
            String email = "user-" + i + "@foo.com";
            String phone = "555-1234";

            byte[] key = Bytes.toBytes(userid);
            Put put = new Put(key);

            put.add(Bytes.toBytes(familyName), Bytes.toBytes("email"), Bytes.toBytes(email));  
            put.add(Bytes.toBytes(familyName), Bytes.toBytes("phone"), Bytes.toBytes(phone));  
            htable.put(put);

        }
        long t4b = System.currentTimeMillis();
        System.out.println("inserted " + total + " users  in " + (t4b - t4a) + " ms");
 
        hConnection.close();
    }
}