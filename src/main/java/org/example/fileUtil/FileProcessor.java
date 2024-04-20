package org.example.fileUtil;

import org.example.entity.Answer;
import org.example.entity.Exam;
import org.example.entity.ExamScore;
import org.example.fileUtil.impl.CSVScoreWriter;
import org.example.fileUtil.impl.JsonAnswerReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    private ExamReaderFactory examReaderFactory = new ExamReaderFactory();
    private ScoreWriter scoreWriter;
    private AnswerReader answerReader;

    public Map<Integer, Exam> readExam(String examsPath) throws IOException {
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
//                        System.out.println("Reading JSON file: " + fileName);

                        // 使用 ExamReaderFactory 创建相应的 ExamReader
                        ExamReader reader = examReaderFactory.createExamReader("json");

                        // 使用 ExamReader 读取文件
                        Exam exam = reader.readExam(file.toString());
                        ExamMap.put(exam.getId(), exam);

                    } else if (fileName.toLowerCase().endsWith(".xml")) {
                        // 读取 XML 文件
                        // TODO: 实现读取 XML 文件的逻辑
//                        System.out.println("Reading XML file: " + fileName);
                        ExamReader reader = examReaderFactory.createExamReader("xml");
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

    public void saveExamScoreList(String outputPath, List<ExamScore> scoreList) throws IOException {
        if (scoreList.isEmpty()){
            System.out.println("no need to save");
            return;
        }
        scoreWriter = new CSVScoreWriter(scoreList);
        scoreWriter.writeScore(outputPath);
    }

    public List<Answer> readAnswer(String answersPath) throws IOException {
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

                        answerReader = new JsonAnswerReader();

                        Answer answer = answerReader.readAnswer(file);

                        answers.add(answer);
                    }
                }
            }

        }
        return answers;
    }
}
