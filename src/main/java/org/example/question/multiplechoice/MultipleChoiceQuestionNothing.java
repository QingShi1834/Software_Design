package org.example.question.multiplechoice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.example.scoreCalculator.impl.FixScoringStrategy;
import org.example.scoreCalculator.impl.NothingScoringStrategy;

@Data
public class MultipleChoiceQuestionNothing extends MultipleChoiceQuestion{

    public MultipleChoiceQuestionNothing() {
        this.setType(2);
        this.setScoreMode("nothing");
    }

    public void initStrategy(){
        this.setScoringStrategy(new NothingScoringStrategy(getAnswer(),getPoints()));
    }


    @Override
    public String toString() {
        return "MultipleChoiceQuestionNothing{" +
                "options=" + getOptions() +
                ", answer=" + getAnswer() +
                ", scoreMode='" + getScoreMode() + '\'' +
                ", id=" + getId() +
                ", type=" + getType() +
                ", question='" + getQuestion() + '\'' +
                ", points=" + getPoints() +
                "}\n";
    }
}
