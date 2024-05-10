package org.example.fileUtil.impl;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.fileUtil.Writer;
import org.example.entity.ExamScore;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CSVWriter<T> implements Writer<T> {

    @Override
    public void write(String filePath, Object content) throws IOException {
        if (content == null) {
            throw new NullPointerException();
        }
        if (content instanceof List<?>) {
            List<T> dataList = (List<T>) content;
            // 创建 CsvMapper 对象
            CsvMapper mapper = new CsvMapper();

            // 创建 CsvSchema 对象
            CsvSchema schema = mapper.schemaFor(dataList.get(0).getClass()).withHeader();
            // 写入 CSV 文件
            mapper.writer(schema).writeValue(new File(filePath), dataList);
        } else {
            System.out.println("格式错误");
        }
    }
}
