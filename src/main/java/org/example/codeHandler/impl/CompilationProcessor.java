package org.example.codeHandler.impl;

import org.example.codeHandler.CodeHandlerFactory;
import org.example.codeHandler.Compiler;
import org.example.codeHandler.Executor;
import org.example.entity.SampleItem;
import org.example.threadPool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 编译型代码处理器
 */
public class CompilationProcessor {

    private final Compiler compiler;
    private final Executor executor;

     // 创建一个线程池
     private final ThreadPool threadPool = ThreadPool.getInstance();

    public CompilationProcessor(CodeHandlerFactory codeHandlerFactory) {
        this.compiler = codeHandlerFactory.createCompiler();
        this.executor = codeHandlerFactory.createExecutor();
    }

    public int handleCode(String filePath, int point, List<SampleItem> sampleList, int timeLimit) {
        String fileAbsolutePath = "src/test/resources/cases/answers/" + filePath;
        // "code-answers/Solution11.java"
        String fileName = filePath.substring(filePath.lastIndexOf('/')+1);

        String compileDirectory =  compiler.getCompileDirectory();

        try {
            // 创建编译任务
            Callable<Boolean> compileTask = () -> compiler.compile(fileAbsolutePath);
            Future<Boolean> compileFuture = threadPool.submit(compileTask);

            // 创建执行任务列表
            List<Callable<String>> executeTasks = new ArrayList<>();
            for (SampleItem sampleItem : sampleList) {
                // 创建编译后的文件的绝对路径
                String absoluteFilePath = compileDirectory + fileName;
                // 创建执行任务
                Callable<String> executeTask = () -> executor.execute(absoluteFilePath, sampleItem.getProgramArgs());
                executeTasks.add(executeTask);
            }

            // 等待编译任务完成
            if (compileFuture.get()) {
                // 执行任务并获取结果
                List<Future<String>> futures = threadPool.invokeAll(executeTasks);

                int len = futures.size();
                for (int i = 0; i < len; i++) {
                   if (! futures.get(i).get().equals(sampleList.get(i).getOutput())){
                       return 0;
                   }
                }
                return point;
            }
            return 0;

        } catch (Exception e) {
            // 处理异常，可以输出日志或者进行其他处理
            e.printStackTrace();
            return 0; // 或者抛出自定义异常，根据实际情况决定
        }

    }

}

//                if (compiler.compile(fileAbsolutePath)) {
//                    // 任⼀⼀个测试⽤例不过均为0分。
//                    for (SampleItem sampleItem : sampleList) {
////                    System.out.println(sampleItem);
//                        String re = executor.execute(compileDirectory + fileName, sampleItem.getProgramArgs());
//                        if (!re.equals(sampleItem.getOutput())){
//                            System.out.println("答案不通过：你的结果 " + re + " 答案： "+sampleItem.getOutput());
//                            return 0;
//                        }
//                    }
//                    return point;
//                } else {
//                    // 编译不成功
//                    System.out.println("Compilation failed for file: " + filePath);
//                    return 0;
//                }