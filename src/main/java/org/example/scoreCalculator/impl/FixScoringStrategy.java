package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.ArrayList;
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
        if (!(self_writtenAnswer instanceof String)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument! The Fix calculateStrategy need String!");
        }
        String selfAnswer = (String)self_writtenAnswer;

        List<Integer> answerList = new ArrayList<Integer>();
        for (char c : selfAnswer.toCharArray()) {
            answerList.add((int) c - 65);
        }

        if (correctAnswer.equals(answerList)) {
            return points;
        }
        // 如果答案部分正确并且没有多余的答案，返回固定的分数
        else if (correctAnswer.containsAll(answerList)) {
            return fixScore;
        }
        return 0;
    }
}
