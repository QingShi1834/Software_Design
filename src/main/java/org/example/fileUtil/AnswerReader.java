package org.example.fileUtil;

import org.example.entity.Answer;

import java.io.File;
import java.io.IOException;

public interface AnswerReader {
    Answer readAnswer(String filePath) throws IOException;
    public Answer readAnswer(File jsonFile) throws IOException;
}
