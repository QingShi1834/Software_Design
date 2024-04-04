package org.example.codeHandler.impl;

import org.example.codeHandler.CodeHandlerAdapter;
import org.example.codeHandler.Compiler;
import org.example.codeHandler.Executor;
import org.example.entity.SampleItem;

import java.io.IOException;
import java.util.List;


public class CompiledLanguageAdapter implements CodeHandlerAdapter {
    private Compiler compiler;
    private Executor executor;

    public CompiledLanguageAdapter(Compiler compiler, Executor executor) {
        this.compiler = compiler;
        this.executor = executor;
    }

    @Override
    public int handleCode(String filePath, int point, List<SampleItem> sampleList, int timeLimit) {
        String fileAbsolutePath = "src/test/resources/cases/answers/" + filePath;
        // "code-answers/Solution11.java"
        String fileName = filePath.substring(filePath.lastIndexOf('/')+1);
        String compileDirectory =  compiler.getCompileDirectory();
        try {
            if (compiler.compile(fileAbsolutePath)) {
                // 任⼀⼀个测试⽤例不过均为0分。
                for (SampleItem sampleItem : sampleList) {
//                    System.out.println(sampleItem);
                    String re = executor.execute(compileDirectory + fileName, sampleItem.getProgramArgs());
                    if (!re.equals(sampleItem.getOutput())){
                        System.out.println("答案不通过：你的结果 " + re + " 答案： "+sampleItem.getOutput());
                        return 0;
                    }
                }
                return point;
            } else {
                // 编译不成功
                System.out.println("Compilation failed for file: " + filePath);
                return 0;
            }
        } catch (Exception e) {
            // 处理异常，可以输出日志或者进行其他处理
            e.printStackTrace();
            return 0; // 或者抛出自定义异常，根据实际情况决定
        }
    }

    public static void main(String[] args) {
//        CodeHandlerAdapter codeHandlerAdapter = new CompiledLanguageAdapter(new JavaCompiler(),new JavaExecutor());
//        int score = codeHandlerAdapter.handleCode("src/test/resources/cases/answers/code-answers/Solution23.java",70,null,0);
//        System.out.println(score);
    }



}
