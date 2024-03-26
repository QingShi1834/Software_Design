package org.example.question.multiplechoice;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
//        setMultipleChoiceScoringStrategy(new PartialMultipleChoiceScoringStrategy(partialScore));
    }

    public void initStrategy(){
        this.setScoringStrategy(new PartialScoringStrategy(getPartialScore(),getAnswer(),getPoints()));
    }

//    @Override
//    public int calculateQuestionScore(Object studentAnswer){
//        if (!(studentAnswer instanceof List)) {
//            throw new IllegalArgumentException("Invalid argument");
//        }
//        return getMultipleChoiceScoringStrategy().calculateQuestionScore(getAnswer(),(List<Integer>) studentAnswer,getPoints());
//    }

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
