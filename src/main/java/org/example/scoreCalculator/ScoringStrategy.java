package org.example.scoreCalculator;

public interface ScoringStrategy {
    public int calculateQuestionScore(Object self_writtenAnswer);
}
