package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

public class NothingScoringStrategy implements ScoringStrategy {
    List<Integer> correctAnswer;
    int points;
    public NothingScoringStrategy(List<Integer> correctAnswer, int points){
        this.correctAnswer = correctAnswer;
        this.points = points;
    }
    public int calculateQuestionScore(Object self_writtenAnswer){
        if (!(self_writtenAnswer instanceof List)) {
            throw new IllegalArgumentException("Invalid argument");
        }

        List<Integer> selfAnswer = (List<Integer>)self_writtenAnswer;
        if (correctAnswer.equals(selfAnswer)) {
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
