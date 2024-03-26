package org.example;

import org.example.answer.Answer;
import org.example.exam.Exam;
import org.example.fileUtil.ExamReader;
import org.example.fileUtil.ExamReaderFactory;
import org.example.fileUtil.impl.JsonAnswerReader;
import org.example.onlineJudge.OnlineJudge;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        String casePath = args[0];
//        // 题目文件夹路径
//        String examsPath = casePath + System.getProperty("file.separator") + "exams";
//        // 答案文件夹路径
//        String answersPath = casePath + System.getProperty("file.separator") + "answers";
//        // 输出文件路径
//        String output = args[1];
        // TODO:在下面调用你实现的功能
        String examsPath = "src/test/resources/cases/exams";
        String answersPath = "src/test/resources/cases/answers";
        // 创建一个 HashMap 实例
        Map<Integer, Exam> idToExamMap = readExam(examsPath);
//        Map<Integer, Answer>
        List<Answer> answers = readAnswer(answersPath);

        OnlineJudge onlineJudge = new OnlineJudge(answers,idToExamMap);
        onlineJudge.calculateExamScoreList();
        onlineJudge.saveExamScoreList();
//        System.out.println(answers.toString());
    }

    private static Map<Integer, Exam> readExam(String examsPath) throws IOException {
        Map<Integer, Exam> ExamMap = new HashMap<>();
        File folder = new File(examsPath);
        if (folder.isDirectory()) {
            // 遍历文件夹
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".json") || name.toLowerCase().endsWith(".xml");
                }
            });

            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".json")) {
                        // 读取 JSON 文件
                        // TODO: 实现读取 JSON 文件的逻辑
                        System.out.println("Reading JSON file: " + fileName);

                        // 使用 ExamReaderFactory 创建相应的 ExamReader
                        ExamReader reader = ExamReaderFactory.createExamReader("json");

                        // 使用 ExamReader 读取文件
                        Exam exam = reader.readExam(file.toString());
                        ExamMap.put(exam.getId(), exam);

                    } else if (fileName.toLowerCase().endsWith(".xml")) {
                        // 读取 XML 文件
                        // TODO: 实现读取 XML 文件的逻辑
                        System.out.println("Reading XML file: " + fileName);
                        ExamReader reader = ExamReaderFactory.createExamReader("xml");
                        // 使用 ExamReader 读取文件
                        Exam exam = reader.readExam(file.toString());
                        ExamMap.put(exam.getId(), exam);
                    }
                }
//                System.out.println(file.getName() + "--->" + ExamMap.toString());
            }

        }
        return ExamMap;
    }

    private static List<Answer> readAnswer(String answersPath) throws IOException {
        File folder = new File(answersPath);
        List<Answer> answers = new ArrayList<>();
        if (folder.isDirectory()) {
            // 遍历文件夹
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".json");
                }
            });

            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".json")) {
                        // 读取 JSON 文件
//                        System.out.println("Reading answer JSON file: " + fileName);

                        JsonAnswerReader reader = new JsonAnswerReader();

                        Answer answer = reader.readAnswer(file);

                        answers.add(answer);
                    }
                }
            }

        }
        return answers;
    }

}