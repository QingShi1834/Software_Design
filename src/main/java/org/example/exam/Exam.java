package org.example.exam;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import org.example.question.Question;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
//@XmlRootElement
public class Exam {
    private int id;
    private String title;
    private long startTime;
    private long endTime;

    private List<Question> questions;

    @Override
    public String toString() {
        return "Exam{\n" +
                "id=" + id +
                "\ntitle='" + title + '\'' +
                "\nstartTime=" + startTime +
                "\nendTime=" + endTime +
                "\nquestions=\n" + questions +
                '}';
    }
}