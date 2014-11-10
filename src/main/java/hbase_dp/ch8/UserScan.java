package hbase_dp.ch8;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class UserScan
{
    static String tableName = "users";
    static String familyName = "info";

    public static void main(String[] args) throws Exception
    {
       
        Configuration config = HBaseConfiguration.create();
        HTable htable = new HTable(config, tableName);

        Map<String, Integer> eventMap = new HashMap<String, Integer>();
        byte[] costColumn = Bytes.toBytes("cost");
        byte[] familyCF = Bytes.toBytes(familyName);
        
        Scan scan = new Scan();
        scan.setCaching(1000);
        scan.addFamily(familyCF);
        ResultScanner scanner = htable.getScanner(scan);
        int recordCount = 0;
        int totalCost = 0;
        long t1 = System.nanoTime();
        try
        {
            for (Result rr : scanner)
            {
                recordCount++;
                KeyValue kv = rr.getColumnLatest(familyCF, costColumn);
                if (kv != null)
                {
                    int cost = Bytes.toInt(kv.getValue());
                    totalCost += cost;
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            scanner.close();
        }
        long t2 = System.nanoTime();
        htable.close();

        System.out
                .println("customer: " + customerId + ",  record_count: " + recordCount + ", total_cost: " + totalCost);
        System.out.println("query time : " + (t2 - t1) / 1000000.0 + " ms\n");
    }
}
