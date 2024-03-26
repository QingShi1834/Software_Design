package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

public class PartialScoringStrategy implements ScoringStrategy {
    private List<Integer> partialScore;
    List<Integer> correctAnswer;
    int points;

    public PartialScoringStrategy(List<Integer> partialScore, List<Integer> correctAnswer, int points) {
        this.partialScore = partialScore;
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
        }else if (correctAnswer.containsAll(selfAnswer)) {
            int score = 0;
            for (Integer ans : selfAnswer) {
                score += this.partialScore.get(ans);  // 假设选项从1开始编号
            }
            return score;
        }
        return 0;
    }
}
