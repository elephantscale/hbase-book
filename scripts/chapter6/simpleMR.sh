hadoop dfs -rmr output
hadoop jar ../target/opr-testbed-1.0-SNAPSHOT-jar-with-dependencies.jar com.opr.data.mr.SimpleMR data/CSE-small.txt output
