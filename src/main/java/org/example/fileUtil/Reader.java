package org.example.fileUtil;

import java.io.File;
import java.io.IOException;

public interface Reader<T> {
    T read(String filePath) throws IOException;
    T read(File file) throws IOException;
}