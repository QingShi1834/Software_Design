package org.example.question.multiplechoice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.example.scoreCalculator.impl.FixScoringStrategy;
@Data
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
