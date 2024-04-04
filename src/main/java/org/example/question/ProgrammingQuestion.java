package org.example.question;

import lombok.Data;
import org.example.entity.SampleItem;
import org.example.scoreCalculator.impl.ProgrammingScoringStrategy;

import java.util.List;

@Data
public class ProgrammingQuestion extends Question{
    private List<SampleItem> samples;
    private int timeLimit;
    private int points;

    public ProgrammingQuestion(){
        this.setType(3);
    }

    public void initStrategy(){
        setScoringStrategy(new ProgrammingScoringStrategy(getPoints(),getSamples(),getTimeLimit()));
    }

    @Override
    public String toString() {
        return "ProgrammingQuestion{" +
                "samples=" + samples +
                ", timeLimit=" + timeLimit +
                ", id=" + getId() +
                ", type=" + getType() +
                ", question='" + getQuestion() + '\'' +
                ", points=" + points +
                "}\n";
    }
}
