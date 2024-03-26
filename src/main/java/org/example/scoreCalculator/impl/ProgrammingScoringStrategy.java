package org.example.scoreCalculator.impl;

import org.example.scoreCalculator.ScoringStrategy;

public class ProgrammingScoringStrategy implements ScoringStrategy {
    int points;
    public ProgrammingScoringStrategy(int points){
        this.points = points;
    }
    @Override
    public int calculateQuestionScore(Object self_writtenAnswer) {
        return points;
    }
}
