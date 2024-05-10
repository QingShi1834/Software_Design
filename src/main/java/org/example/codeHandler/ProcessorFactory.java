package org.example.codeHandler;

import org.example.codeHandler.impl.JavaCodeProcessor;

public class ProcessorFactory {

    public static ProcessorTemplate getProcessor(String codeType){
        switch (codeType){
            case "java":return new JavaCodeProcessor();
            case "python":break;
            default:break;
        }

        throw new IllegalArgumentException("Unsupported code type: " + codeType);

    }
}
