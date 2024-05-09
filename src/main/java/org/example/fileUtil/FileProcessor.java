package org.example.fileUtil;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.entity.Answer;
import org.example.entity.Complexity;
import org.example.entity.Exam;
import org.example.entity.ExamScore;
import org.example.fileUtil.impl.CSVScoreWriter;
import org.example.fileUtil.impl.JsonReader;
import org.example.fileUtil.impl.XmlReader;
import org.example.question.multiplechoice.MultipleChoiceQuestion;
import org.example.question.multiplechoice.MultipleChoiceQuestionDeserializer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileProcessor {
//    private ExamReaderFactory examReaderFactory = new ExamReaderFactory();
//    private ScoreWriter scoreWriter;
//    private AnswerReader answerReader;

    public Map<Integer, Exam> readExam(String examsPath) throws IOException {
        Map<Integer, Exam> ExamMap = new HashMap<>();
        File folder = new File(examsPath);
        if (folder.isDirectory()) {
            // 遍历文件夹
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json") || name.toLowerCase().endsWith(".xml"));
            JsonReader<Exam> reader = new JsonReader<>(Exam.class);
            reader.addDeserializer(MultipleChoiceQuestion.class, new MultipleChoiceQuestionDeserializer());
            XmlReader xmlReader = new XmlReader();

            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".json")) {

                        Exam exam = reader.read(file.toString());
//                        // 使用 ExamReaderFactory 创建相应的 ExamReader
//                        ExamReader reader = examReaderFactory.createExamReader("json");
//
//                        // 使用 ExamReader 读取文件
//                        Exam exam = reader.readExam(file.toString());
                        ExamMap.put(exam.getId(), exam);

                    } else if (fileName.toLowerCase().endsWith(".xml")) {
                        // 读取 XML 文件
                        // TODO: 实现读取 XML 文件的逻辑
//                        System.out.println("Reading XML file: " + fileName);
//                        ExamReader reader = examReaderFactory.createExamReader("xml");
//                        // 使用 ExamReader 读取文件
                        Exam exam = xmlReader.read(file.toString());
                        ExamMap.put(exam.getId(), exam);
                    }
                }
            }

        }
        return ExamMap;
    }

    public void saveExamScoreList(String outputPath, List<ExamScore> scoreList) throws IOException {
        if (scoreList.isEmpty()){
            System.out.println("no need to save");
            return;
        }
        CSVScoreWriter scoreWriter = new CSVScoreWriter(scoreList);
        scoreWriter.writeScore(outputPath);
    }

    public List<Answer> readAnswer(String answersPath) throws IOException {
        File folder = new File(answersPath);
        List<Answer> answers = new ArrayList<>();
        if (folder.isDirectory()) {
            // 遍历文件夹
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".json");
                }
            });

            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".json")) {
                        // 读取 JSON 文件
//                        System.out.println("Reading answer JSON file: " + fileName);

//                        answerReader = new JsonAnswerReader();

                        JsonReader<Answer> reader = new JsonReader<>(Answer.class);
                        Answer answer = reader.read(file);

                        answers.add(answer);
                    }
                }
            }

        }
        return answers;
    }

    public void saveComplexityList(String outputPath, List<Complexity> complexityList) throws IOException{
        if (complexityList.isEmpty()){
            System.out.println("no need to save");
            return;
        }
        // 创建 CsvMapper 对象
        CsvMapper mapper = new CsvMapper();

        // 创建 CsvSchema 对象
        CsvSchema schema = mapper.schemaFor(Complexity.class).withHeader();
//        System.out.println("正在写入csv文件： " + filePath);
        // 写入 CSV 文件
        mapper.writer(schema).writeValue(new File(outputPath), complexityList);
    }
}
