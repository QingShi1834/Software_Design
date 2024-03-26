package org.example.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.example.question.multiplechoice.MultipleChoiceQuestion;
import org.example.question.singlechoice.SingleChoiceQuestion;
import org.example.scoreCalculator.ScoringStrategy;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleChoiceQuestion.class, name = "1"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "2"),
        @JsonSubTypes.Type(value = ProgrammingQuestion.class, name = "3")
})
@Data
public abstract class Question<T> {

    private int id;
    private int type;
    private String question;
    private int points;

    private ScoringStrategy scoringStrategy;

    public void initStrategy(){}

    public int calculateQuestionScore(Integer studentAnswer){
        return 0;
    }
    public int calculateQuestionScore(String studentAnswer){
        return 0;
    }

    public int calculateQuestionScore(Object answer){
        return 0;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type=" + type +
                ", question='" + question + '\'' +
                ", points=" + points +
                '}';
    }
}