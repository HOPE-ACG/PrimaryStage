package com.acg.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS static tools class
 *
 * @author acg
 */
public class HDFSUtils {

    /**
     * Acquire HDFS file system
     */
    public static FileSystem getFS(Configuration config, String uri, String user) throws URISyntaxException, IOException, InterruptedException {

        return FileSystem.get(new URI(uri), config, user);
    }

    /**
     * Close HDFS resource
     */
    public static void closeFs(FileSystem fs) throws IOException {

        fs.close();
    }
}
