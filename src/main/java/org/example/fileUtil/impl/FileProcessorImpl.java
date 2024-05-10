package org.example.fileUtil.impl;
import org.example.entity.Answer;
import org.example.entity.Complexity;
import org.example.entity.Exam;
import org.example.entity.ExamScore;
import org.example.fileUtil.FileProcessor;
import org.example.question.multiplechoice.MultipleChoiceQuestion;
import org.example.question.multiplechoice.MultipleChoiceQuestionDeserializer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileProcessorImpl implements FileProcessor {

    public Map<Integer, Exam> readExam(String examsPath) throws IOException {
        Map<Integer, Exam> ExamMap = new HashMap<>();
        File folder = new File(examsPath);
        if (folder.isDirectory()) {
            // 遍历文件夹
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json") || name.toLowerCase().endsWith(".xml"));
            JsonReader<Exam> reader = new JsonReader<>(Exam.class);
            reader.addDeserializer(MultipleChoiceQuestion.class, new MultipleChoiceQuestionDeserializer());
            XmlReader xmlReader = new XmlReader();

            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".json")) {
                        // 读取 Json 文件
                        Exam exam = reader.read(file.toString());
                        ExamMap.put(exam.getId(), exam);

                    } else if (fileName.toLowerCase().endsWith(".xml")) {
                        // 读取 XML 文件
                        Exam exam = xmlReader.read(file.toString());
                        ExamMap.put(exam.getId(), exam);
                    }
                }
            }

        }
        return ExamMap;
    }

    public void saveExamScoreList(String outputPath, List<ExamScore> scoreList) throws IOException {
        if (scoreList.isEmpty()){
            System.out.println("no need to save");
            return;
        }
        CSVWriter scoreWriter = new CSVWriter();
        scoreWriter.write(outputPath,scoreList);
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

                        JsonReader<Answer> reader = new JsonReader<>(Answer.class);
                        Answer answer = reader.read(file);

                        answers.add(answer);
                    }
                }
            }

        }
        return answers;
    }

    public void saveComplexityList(String outputPath, List<Complexity> complexityList) throws IOException{
        if (complexityList.isEmpty()){
            System.out.println("no need to save");
            return;
        }
        CSVWriter scoreWriter = new CSVWriter();
        scoreWriter.write(outputPath,complexityList);

    }
}
