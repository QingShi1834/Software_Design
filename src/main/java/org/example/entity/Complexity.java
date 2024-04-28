package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Complexity {
    @JsonProperty(index = 0)
    private int examId;
    @JsonProperty(index = 1)
    private int stuId;
    @JsonProperty(value = "qId",index = 2)
    private int qId;
    @JsonProperty(index = 3)
    private int complexity;

    // 构造函数
    public Complexity(int examId, int stuId, int qId, int complexity) {
        this.examId = examId;
        this.stuId = stuId;
        this.qId = qId;
        this.complexity = complexity;
    }

    @Override
    public String toString() {
        return "ExamScore{" +
                "examId='" + examId + '\'' +
                ", stuId='" + stuId + '\'' +
                ", qId=" + qId +
                ", complexity=" + complexity +
                '}';
    }
}
