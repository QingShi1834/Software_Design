package org.example.codeHandler.impl;

import org.example.codeHandler.CodeHandlerAdapter;
import org.example.codeHandler.CodeHandlerFactory;
import org.example.codeHandler.Compiler;
import org.example.codeHandler.Executor;
import org.example.entity.SampleItem;

import java.io.IOException;
import java.util.List;

public class InterpretedLanguageAdapter implements CodeHandlerAdapter {
    private Executor executor;
    public InterpretedLanguageAdapter(Executor executor) {
        this.executor = executor;
    }

    @Override
    public int handleCode(String filePath, int point, List<SampleItem> sampleList, int timeLimit) {
        // TODO 下面逻辑上有问题，今天没时间改
        for (SampleItem sampleItem : sampleList) {
            String re = executor.execute(filePath,sampleItem.getProgramArgs());
            if (!re.equals(sampleItem.getOutput())){
                return 0;
            }
        }
        return point;
    }
}
