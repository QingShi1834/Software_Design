package org.example.question.multiplechoice;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.example.question.Question;
import org.example.scoreCalculator.ScoringStrategy;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "scoreMode")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceQuestionFix.class, name = "fix"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestionPartial.class, name = "partial"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestionNothing.class, name = "nothing"),
})

@Data
public class MultipleChoiceQuestion extends Question {
    private String scoreMode;
//    @JacksonXmlProperty(localName = "option")
//    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> options;
    @JsonProperty("answer")
    @JsonAlias("answers")
    private List<Integer> answer;

    public MultipleChoiceQuestion() {
        this.setType(2);
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestionMood{" +
                "options=" + options +
                ", answer=" + answer +
                ", scoreMode='" + scoreMode + '\'' +
                ", id=" + getId() +
                ", type=" + getType() +
                ", question='" + getQuestion() + '\'' +
                ", points=" + getPoints() +
                "}\n";
    }

}
