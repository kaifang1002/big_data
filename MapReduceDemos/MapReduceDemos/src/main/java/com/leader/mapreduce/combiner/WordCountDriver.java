package com.leader.mapreduce.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        // 1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        // 3.关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4.设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass((IntWritable.class));

        // 5.设置最终输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setCombinerClass(WordCountCombiner.class);
        job.setNumReduceTasks(0);

        // 6.设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\role.txt"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\output"));

        // 7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}