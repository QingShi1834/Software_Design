package org.example.codeHandler.impl;

import org.example.codeHandler.Compiler;
import org.example.codeHandler.Executor;
import org.example.codeHandler.ProcessorTemplate;

public class JavaCodeProcessor extends ProcessorTemplate {

    @Override
    protected Compiler createCompiler() {
        return new JavaCompiler();
    }

    @Override
    protected Executor createExecutor() {
        return new JavaExecutor();
    }

    @Override
    protected boolean isCompiledLanguage() {
        return true;
    }

}
