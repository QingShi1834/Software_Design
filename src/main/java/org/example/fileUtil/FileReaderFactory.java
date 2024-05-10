package org.example.fileUtil;

import org.example.entity.Exam;
import org.example.fileUtil.impl.JsonReader;
import org.example.fileUtil.impl.XmlReader;

public class FileReaderFactory {
    public static <T> Reader<T> createFileReader(String fileType, Class<T> type) {
        if ("json".equalsIgnoreCase(fileType)) {
            return new JsonReader<>(type);
        } else if ("xml".equalsIgnoreCase(fileType)) {
            return (Reader<T>)new XmlReader();
        }
        throw new IllegalArgumentException("Unsupported file type: " + fileType);
    }
}
