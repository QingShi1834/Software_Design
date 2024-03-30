package org.example.fileUtil.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.entity.Exam;
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
}
