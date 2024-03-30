package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Answer {
    @JsonProperty("examId")
    private int examId;

    @JsonProperty("stuId")
    private int studentId;

    @JsonProperty("submitTime")
    private long submitTime;

    @JsonProperty("answers")
    private List<AnswerItem> answers;

    @Override
    public String toString() {
        return "Answer{" +
                "\nexamId=" + examId +
                "\nstuId=" + studentId +
                "\nsubmitTime=" + submitTime +
                "\nanswers=" + answers +
                "\n}";
    }

}
