package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

public class SingleChoiceScoringStrategy implements ScoringStrategy {
    int answer;

    int points;
    public SingleChoiceScoringStrategy(int answer, int points){
        this.answer = answer;
        this.points = points;
    }
    @Override
    public int calculateQuestionScore(Object self_writtenAnswer) {
        if (!(self_writtenAnswer instanceof Integer)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument");
        }
        if (answer == (int)self_writtenAnswer){
            return points;
        }
        return 0;
    }
}
