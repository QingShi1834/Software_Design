package org.example.scoreCalculator.impl;

import org.example.codeHandler.ProcessorTemplate;
import org.example.codeHandler.impl.JavaCodeProcessor;
import org.example.entity.SampleItem;
import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

public class ProgrammingScoringStrategy implements ScoringStrategy {
    private int points;
    private List<SampleItem> samples;
    private int timeLimit;

//    CodeHandlerAdapter codeHandlerAdapter;
    public ProgrammingScoringStrategy(int points,List<SampleItem> samples, int timeLimit){
        this.points = points;
        this.samples = samples;
        this.timeLimit = timeLimit;
    }
    @Override
    public int calculateQuestionScore(Object self_writtenAnswer) {
        if (!(self_writtenAnswer instanceof String)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument! The Nothing calculateStrategy need String!");
        }
//        String answer_path = "src/test/resources/cases/answers/" + (String)self_writtenAnswer;
        String answer_path = (String)self_writtenAnswer;

        switch (getFileExtension((String)self_writtenAnswer)){
            case "java":
                return new JavaCodeProcessor().handleCode(answer_path,points,samples,timeLimit);
            default:break;
        }

        return points;
    }

//    public int calculateComplexity(String self_writtenAnswer){
//        ProcessorTemplate processorTemplate = new JavaCodeProcessor();
//        return processorTemplate.getCyclomaticComplexity(self_writtenAnswer);
//    }

    // 获取文件的后缀名
    private static String getFileExtension(String filePath) {
        if (filePath != null) {
            int dotIndex = filePath.lastIndexOf('.');
            if (dotIndex != -1 && dotIndex < filePath.length() - 1) {
                return filePath.substring(dotIndex + 1);
            }
        }
        return null;
    }
}
