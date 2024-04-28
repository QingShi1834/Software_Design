package org.example.codeHandler;

import com.github.javaparser.ast.Node;

public interface Preprocessor {
    boolean compile(String filePath);
    String getCompileDirectory();
    int calculateMethodCyclomaticComplexity(Node methodCode);
    int calculateClassCyclomaticComplexity(String filePath);
}
