package org.example.codeHandler.impl;

import org.example.codeHandler.Preprocessor;
import org.example.codeHandler.Executor;
import org.example.codeHandler.ProcessorTemplate;

public class JavaCodeProcessor extends ProcessorTemplate {

    @Override
    protected Preprocessor createPreprocessor() {
        return new JavaPreprocessor();
    }

    @Override
    protected Executor createExecutor() {
        return new JavaExecutor();
    }

    @Override
    protected boolean isCompiledLanguage() {
        return true;
    }

    @Override
    public int getCyclomaticComplexity(String filePath) {
        Preprocessor javaPreprocessor = createPreprocessor();
        return javaPreprocessor.calculateClassCyclomaticComplexity(filePath);
    }
}
