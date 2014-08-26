# disable and drop the HBase table
echo "disable 'wordcount'" | hbase shell
echo "drop 'wordcount'" | hbase shell
# create new table
echo "create 'wordcount', {NAME => 'f'},   {SPLITS => ['g', 'm', 'r', 'w']}" | hbase shell
# create HFiles
sudo -u hdfs hadoop dfs -chown -R "$USER":"$USER" /user/"$USER"/output
hadoop dfs -rm -r -skipTrash output/wordcount
hadoop jar $HBASE_HOME/hbase.jar importtsv \
-Dimporttsv.separator=, \
-Dimporttsv.bulk.output=output/wordcount \
-Dimporttsv.columns=HBASE_ROW_KEY,f:count wordcount data/word_count.csv
# fix file ownership and load them into the table
sudo -u hdfs hadoop dfs -chown -R hbase:hbase /user/"$USER"/output/wordcount
hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles output/wordcount wordcount
