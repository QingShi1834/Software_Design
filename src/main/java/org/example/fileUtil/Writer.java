package org.example.fileUtil;

import java.io.IOException;

public interface Writer<T> {
    void write(String filePath, T content) throws IOException;
}
