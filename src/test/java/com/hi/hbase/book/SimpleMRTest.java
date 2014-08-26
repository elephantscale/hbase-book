package com.hi.hbase.book;

import com.elephantscale.hbase.book.chapter1.SimpleMR;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class SimpleMRTest {
    private static final Logger logger = LoggerFactory.getLogger(SimpleMRTest.class);
    /**
     * Test of main method, of class SimpleMR.
     */
    @Test
    public void testMain() throws Exception {
        String[] args = new String[2];
        args[0] = "data/small-file";
        args[1] = "output";
        // since this function runs either in the IDE or as part of 'mvn install', we can use local file command 
        // to delete the output directory. We are using the Apache commons io, 
        // which will work correctly if the directory is not present.      
        logger.debug("Delete directory: {}", args[1]);
        FileUtils.deleteDirectory(new File(args[1]));
        logger.debug("Two arguments: {} and {}", args[0], args[1]);
        SimpleMR.main(args);
        assertTrue (true);
    }
}