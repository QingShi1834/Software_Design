package org.example.onlineJudge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExamScore {
    @JsonProperty(index = 0)

    private int examId;
    @JsonProperty(index = 1)
    private int stuId;
    @JsonProperty(index = 2)
    private int score;

    // 构造函数
    public ExamScore(int examId, int stuId, int score) {
        this.examId = examId;
        this.stuId = stuId;
        this.score = score;
    }

    // Getters and setters
    // 省略 getter 和 setter 方法

    // toString 方法，用于输出成绩信息
    @Override
    public String toString() {
        return "ExamScore{" +
                "examId='" + examId + '\'' +
                ", stuId='" + stuId + '\'' +
                ", score=" + score +
                '}';
    }
}
