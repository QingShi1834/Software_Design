package org.example.fileUtil.impl;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.fileUtil.ScoreWriter;
import org.example.entity.ExamScore;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CSVScoreWriter implements ScoreWriter {
    private List<ExamScore> examScoreList;
    public CSVScoreWriter(List<ExamScore> examScoreList){
        this.examScoreList = examScoreList;
    }
    @Override
    public void writeScore(String filePath) throws IOException {

        // 创建 CsvMapper 对象
        CsvMapper mapper = new CsvMapper();

        // 创建 CsvSchema 对象
        CsvSchema schema = mapper.schemaFor(ExamScore.class).withHeader();

        // 写入 CSV 文件
        mapper.writer(schema).writeValue(new File(filePath), examScoreList);
    }
}
