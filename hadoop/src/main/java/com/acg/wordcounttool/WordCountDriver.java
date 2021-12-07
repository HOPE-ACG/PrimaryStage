package com.acg.wordcounttool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

public class WordCountDriver {

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        WordCountTool tool;
        if ("wordcount".equals(args[0])) {
            tool = new WordCountTool();
        } else {
            throw new RuntimeException("Wrong Input's arguments");
        }
        try {
            System.exit(
                    ToolRunner.run(conf, tool, Arrays.copyOfRange(args, 1, args.length))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
