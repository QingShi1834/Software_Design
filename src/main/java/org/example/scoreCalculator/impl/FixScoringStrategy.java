package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

public class FixScoringStrategy implements ScoringStrategy {
    private int fixScore;
    List<Integer> correctAnswer;
    int points;
    public FixScoringStrategy(int points, int fixScore, List<Integer> correctAnswer) {
        this.fixScore = fixScore;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }
    public int calculateQuestionScore(Object self_writtenAnswer){
        if (!(self_writtenAnswer instanceof List)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument");
        }
        List<Integer> selfAnswer = (List<Integer>)self_writtenAnswer;
        if (correctAnswer.equals(self_writtenAnswer)) {
            return points;
        }
        // 如果答案部分正确并且没有多余的答案，返回固定的分数
        else if (correctAnswer.containsAll(selfAnswer)) {
            return fixScore;
        }
        return 0;
    }
}
