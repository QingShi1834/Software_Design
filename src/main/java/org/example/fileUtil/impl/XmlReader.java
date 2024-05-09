package org.example.fileUtil.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.sf.json.xml.XMLSerializer;
import org.example.entity.Exam;
import org.example.question.multiplechoice.MultipleChoiceQuestionDeserializer;
import org.example.fileUtil.Reader;
import org.example.question.multiplechoice.MultipleChoiceQuestion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlReader implements Reader<Exam> {
    @Override
    public Exam read(File file) throws IOException {
        return null;
    }

    @Override
    public Exam read(String filePath) throws IOException {
//        return null;
        try {

            // 读取 XML 文件内容并转换为字符串
            String xmlString = new String(Files.readAllBytes(Paths.get(filePath)));

            //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
            XMLSerializer xmlSerializer = new XMLSerializer();
            String xml2json = xmlSerializer.read(xmlString).toString();

            return readExamByJson(xml2json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Exam readExamByJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MultipleChoiceQuestion.class, new MultipleChoiceQuestionDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(json, Exam.class);
    }
}
