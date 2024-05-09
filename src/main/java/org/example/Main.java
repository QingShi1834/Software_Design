package org.example;

import org.example.onlineJudge.OnlineJudge;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String casePath = args[0];
        // 题目文件夹路径
        String examsPath = casePath + System.getProperty("file.separator") + "exams";
        // 答案文件夹路径
        String answersPath = casePath + System.getProperty("file.separator") + "answers";
        // 输出文件路径
        String output = args[1];
        // 将提交的回答放入oj判断，拿到成绩表
        OnlineJudge onlineJudge = new OnlineJudge(examsPath, answersPath, output);
        onlineJudge.run();
    }


}