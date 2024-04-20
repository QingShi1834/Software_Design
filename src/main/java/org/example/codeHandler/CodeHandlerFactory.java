package org.example.codeHandler;

public interface CodeHandlerFactory {
    Compiler createCompiler();
    Executor createExecutor();
    boolean isCompiledLanguage();
}
