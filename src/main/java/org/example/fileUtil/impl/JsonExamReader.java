package org.example.fileUtil.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.exam.Exam;
import org.example.fileUtil.ExamReader;
import org.example.fileUtil.MultipleChoiceQuestionDeserializer;
import org.example.question.multiplechoice.MultipleChoiceQuestion;

import java.io.File;
import java.io.IOException;

public class JsonExamReader implements ExamReader {
    @Override
    public Exam readExam(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MultipleChoiceQuestion.class, new MultipleChoiceQuestionDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(new File(filePath), Exam.class);
    }

    public static Exam readExamByJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MultipleChoiceQuestion.class, new MultipleChoiceQuestionDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(json, Exam.class);
    }
    public static void main(String[] args) {
        JsonExamReader  jsonExamReader =   new JsonExamReader();
        try {
//            System.out.println(jsonExamReader.readExam("D:/NJU/软件系统设计/hello.json").toString());
            System.out.println(jsonExamReader.readExam("D:/NJU/temp.json").toString());
//            System.out.println(jsonExamReader.readExam("src/test/resources/cases/exams/1.json").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
