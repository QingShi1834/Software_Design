package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

import java.util.ArrayList;
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
        if (!(self_writtenAnswer instanceof String)) {
            System.out.println(self_writtenAnswer.toString());
            throw new IllegalArgumentException("Invalid argument! The SingleChoice calculateStrategy need String!");
        }
        String selfAnswer = (String) self_writtenAnswer;

        if (answer == (int)selfAnswer.charAt(0) - 65){
            return points;
        }
        return 0;
    }
}
