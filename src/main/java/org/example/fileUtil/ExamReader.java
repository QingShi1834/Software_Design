package org.example.fileUtil;

import org.example.exam.Exam;

import java.io.IOException;

public interface ExamReader {
    Exam readExam(String filePath) throws IOException;
}

