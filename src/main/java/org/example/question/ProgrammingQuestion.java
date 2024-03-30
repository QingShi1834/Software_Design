package org.example.question;

import lombok.Data;
import org.example.scoreCalculator.impl.FixScoringStrategy;
import org.example.scoreCalculator.impl.ProgrammingScoringStrategy;

import java.util.List;
import java.util.Map;
@Data
public class ProgrammingQuestion extends Question{
    private List<Map<String, String>> samples;
    private int timeLimit;
    private int points;

    public ProgrammingQuestion(){
        this.setType(3);
    }

    public void initStrategy(){
        setScoringStrategy(new ProgrammingScoringStrategy(getPoints()));
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
