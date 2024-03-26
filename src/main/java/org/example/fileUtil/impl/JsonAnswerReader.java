package org.example.fileUtil.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.answer.Answer;
import org.example.fileUtil.AnswerReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JsonAnswerReader implements AnswerReader {
    @Override
    public Answer readAnswer(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Paths.get(filePath).toFile(), Answer.class);
    }

    public Answer readAnswer(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonFile, Answer.class);
    }
}
