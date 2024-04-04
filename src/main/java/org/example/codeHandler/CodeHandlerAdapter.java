package org.example.codeHandler;

import org.example.entity.SampleItem;

import java.io.IOException;
import java.util.List;

public interface CodeHandlerAdapter {
    public int handleCode(String filePath, int point, List<SampleItem>sampleList, int timeLimit);
}
