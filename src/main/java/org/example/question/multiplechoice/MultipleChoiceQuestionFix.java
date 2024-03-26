package org.example.question.multiplechoice;

import org.example.scoreCalculator.impl.FixScoringStrategy;

public class MultipleChoiceQuestionFix extends MultipleChoiceQuestion{
    private int fixScore;

    public MultipleChoiceQuestionFix() {
        this.setType(2);
        this.setScoreMode("fix");
    }
    public int getFixScore() {
        return fixScore;
    }

    public void setFixScore(int fixScore) {
        this.fixScore = fixScore;
    }

    public void initStrategy(){
        this.setScoringStrategy(new FixScoringStrategy(getPoints(),fixScore,getAnswer()));
    }
//    public int calculateQuestionScore(Object studentAnswer){
//        if (!(studentAnswer instanceof List)) {
//            throw new IllegalArgumentException("Invalid argument");
//        }
//
//        return getMultipleChoiceScoringStrategy().calculateQuestionScore(getAnswer(),(List<Integer>)studentAnswer,getPoints());
//    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestionFix{" +
                "fixScore=" + fixScore +
                ", options=" + getOptions() +
                ", answer=" + getAnswer() +
                ", scoreMode='" + getScoreMode() + '\'' +
                ", id=" + getId() +
                ", type=" + getType() +
                ", question='" + getQuestion() + '\'' +
                ", points=" + getPoints() +
                "}\n";
    }
}
