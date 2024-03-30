package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.ArrayList;
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
        if (!(self_writtenAnswer instanceof String)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument! The Partial calculateStrategy need String!");
        }
        String selfAnswer = (String)self_writtenAnswer;

        List<Integer> answerList = new ArrayList<Integer>();
        for (char c : selfAnswer.toCharArray()) {
            answerList.add((int) c - 65);
        }
        if (correctAnswer.equals(answerList)) {
            return points;
        }else if (correctAnswer.containsAll(answerList)) {
            int score = 0;
            for (Integer ans : answerList) {
                score += this.partialScore.get(ans);
            }
            return score;
        }
        return 0;
    }
}
