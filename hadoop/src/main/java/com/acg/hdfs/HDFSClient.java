package com.acg.hdfs;

import com.acg.util.HDFSUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URISyntaxException;

public class HDFSClient {

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {

        //获取文件系统
        Configuration config = new Configuration();
        FileSystem fs = HDFSUtils.getFS(config, "hdfs://hadoop101:8020", "com/acg");

        //新建文件夹//
        fs.mkdirs(new Path("/sanguo/caocao"));
        //上传文件
        fs.copyFromLocalFile(false, false, new Path("D:\\\\hadoop\\command.txt"),
                new Path("/sanguo/caocao"));
        //下载文件
        fs.copyToLocalFile(false, new Path("/input/"), new Path("D:\\\\hadoop\\family.txt"), true);

        //关闭资源
        HDFSUtils.closeFs(fs);
    }
}
