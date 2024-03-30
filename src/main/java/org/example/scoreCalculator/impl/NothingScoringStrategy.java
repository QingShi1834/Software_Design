package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;

public class NothingScoringStrategy implements ScoringStrategy {
    List<Integer> correctAnswer;
    int points;
    public NothingScoringStrategy(List<Integer> correctAnswer, int points){
        this.correctAnswer = correctAnswer;
        this.points = points;
    }
    public int calculateQuestionScore(Object self_writtenAnswer){
        if (!(self_writtenAnswer instanceof String)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument! The Nothing calculateStrategy need String!");
        }
        String selfAnswer = (String)self_writtenAnswer;

        List<Integer> answerList = new ArrayList<Integer>();
        for (char c : selfAnswer.toCharArray()) {
            answerList.add((int) c - 65);
        }
        if (correctAnswer.equals(answerList)) {
            return points;
        }
        return 0;
    }
//    public int calculateQuestionScore(Object answer){
//        if (!(answer instanceof List)) {
//            throw new IllegalArgumentException("Invalid argument");
//        }
//
//        List<Integer> answerList = (List<Integer>) answer;
//
//        // 如果答案完全正确，返回问题的总分
//        if (this.getAnswer().equals(answerList)) {
//            return this.getPoints();
//        }
//        // 否则，返回 0
//        return 0;
//    }
}
