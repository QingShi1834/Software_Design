package org.example.codeHandler;

public interface Compiler {
    boolean compile(String filePath);
    String getCompileDirectory();
}