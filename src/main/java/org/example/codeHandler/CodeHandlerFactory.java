package org.example.codeHandler;

import org.example.entity.SampleItem;

import java.util.List;

public interface CodeHandlerFactory {
    Compiler createCompiler();
    Executor createExecutor();
    int handleCode(String filePath, int point, List<SampleItem> sampleList, int timeLimit);
}
