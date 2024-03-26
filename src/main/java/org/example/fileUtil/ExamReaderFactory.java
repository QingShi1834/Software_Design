package org.example.fileUtil;

import org.example.fileUtil.impl.JsonExamReader;
import org.example.fileUtil.impl.XmlExamReader;

public class ExamReaderFactory {
    public static ExamReader createExamReader(String fileExtension) {
        switch (fileExtension) {
            case "json":
                return new JsonExamReader();
            case "xml":
                return new XmlExamReader();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }
}
