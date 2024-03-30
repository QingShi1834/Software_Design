package org.example.question.multiplechoice;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.example.scoreCalculator.impl.FixScoringStrategy;
import org.example.scoreCalculator.impl.PartialScoringStrategy;

import java.util.List;
@Data
public class MultipleChoiceQuestionPartial extends MultipleChoiceQuestion{
    @JsonProperty("partialScore")
    @JsonAlias("partialScores")
    private List<Integer> partialScore;
    public MultipleChoiceQuestionPartial(){
        this.setType(2);
        this.setScoreMode("partial");
        this.setScoringStrategy(new PartialScoringStrategy(partialScore,getAnswer(),getPoints()));
    }

    public void initStrategy(){
        this.setScoringStrategy(new PartialScoringStrategy(getPartialScore(),getAnswer(),getPoints()));
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestionPartial{" +
                "partialScore=" + partialScore +
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
