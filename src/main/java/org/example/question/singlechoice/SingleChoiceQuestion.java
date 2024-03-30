package org.example.question.singlechoice;

import lombok.Data;
import org.example.question.Question;
import org.example.scoreCalculator.impl.SingleChoiceScoringStrategy;

import java.util.List;

@Data
public class SingleChoiceQuestion extends Question {
    private List<String> options;
    private int answer;

    public SingleChoiceQuestion(){
        this.setType(1);
    }

    public void initStrategy(){
        this.setScoringStrategy(new SingleChoiceScoringStrategy(getAnswer(),getPoints()));
    }

    @Override
    public String toString() {
        return "SingleChoiceQuestion{" +
                "options=" + options +
                ", answer=" + answer +
                ", id=" + getId() +
                ", type=" + getType() +
                ", question='" + getQuestion() + '\'' +
                ", points=" + getPoints() +
                "}\n";
    }
}
