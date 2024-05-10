package org.example.fileUtil;

import org.example.entity.Answer;
import org.example.entity.Complexity;
import org.example.entity.Exam;
import org.example.entity.ExamScore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileProcessor {
    List<Answer> readAnswer(String answersPath) throws IOException;
    Map<Integer, Exam> readExam(String examsPath) throws IOException;
    void saveExamScoreList(String outputPath, List<ExamScore> scoreList) throws IOException;
    void saveComplexityList(String outputPath, List<Complexity> complexityList) throws IOException;

}
