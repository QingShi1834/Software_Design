package org.example.codeHandler.impl;

import org.example.codeHandler.CodeHandlerFactory;
import org.example.codeHandler.Compiler;
import org.example.codeHandler.Executor;

public class JavaHandlerFactory implements CodeHandlerFactory {
    @Override
    public Compiler createCompiler() {
        return new JavaCompiler();
    }

    @Override
    public Executor createExecutor() {
        return new JavaExecutor();
    }

    @Override
    public boolean isCompiledLanguage() {
        return true;
    }
}
