package org.example.codeHandler;
import org.example.entity.SampleItem;

import org.example.threadPool.ThreadPool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public abstract class ProcessorTemplate {
    private ThreadPool threadPool = ThreadPool.getInstance();
//    @Getter
//    private int cyclomaticComplexity = -1;
    // 工厂方法，由子类实现
    protected abstract Preprocessor createPreprocessor();

    // 抽象方法，由子类实现
    public abstract int getCyclomaticComplexity(String filePath);

    // 工厂方法，由子类实现
    protected abstract Executor createExecutor();

    // 抽象方法，由子类实现
    protected abstract boolean isCompiledLanguage();

    // 模板方法，定义了处理代码的通用流程
    public final int handleCode(String filePath, int point, List<SampleItem> sampleList, int timeLimit) {
        String fileAbsolutePath = "src/test/resources/cases/answers/" + filePath;
        // "code-answers/Solution11.java"
        String fileName = filePath.substring(filePath.lastIndexOf('/')+1);

        Executor executor = createExecutor();

        List<String> outputList = sampleList.stream()
                .map(SampleItem::getOutput)
                .collect(Collectors.toList());

        System.out.println(fileName);
        try {
            // 钩子函数
            if (isCompiledLanguage()){
                Preprocessor preprocessor = createPreprocessor();

                String compiledFilePath =  preprocessor.getCompileDirectory() + fileName;
                // 创建编译任务
                Future<Boolean> compileFuture = compileFileTask(preprocessor, fileAbsolutePath);
                System.out.println(compiledFilePath);
                // 创建执行任务列表
                List<Callable<String>> executeTasks = sampleList.parallelStream()
                        .map(sampleItem -> (Callable<String>) () -> executor.execute(compiledFilePath, sampleItem.getProgramArgs()))
                        .collect(Collectors.toList());

                // 等待编译任务完成
                if (compileFuture.get()) {
                    return executeTasks(executeTasks,point,timeLimit,outputList);
                }
            }else {
                // 创建执行任务列表
                List<Callable<String>> executeTasks = sampleList.parallelStream()
                        .map(sampleItem -> (Callable<String>) () -> executor.execute(fileAbsolutePath, sampleItem.getProgramArgs()))
                        .collect(Collectors.toList());

                return executeTasks(executeTasks,point,timeLimit,outputList);
            }

            return 0;

        } catch (Exception e) {
            // 处理异常，可以输出日志或者进行其他处理
            e.printStackTrace();
            return 0; // 或者抛出自定义异常，根据实际情况决定
        }

    }

    // 具体方法，通用的编译文件流程
    protected Future<Boolean> compileFileTask(Preprocessor preprocessor, String fileAbsolutePath) {
        Callable<Boolean> compileTask = () -> preprocessor.compile(fileAbsolutePath);
        return threadPool.submit(compileTask);
    }

    // 具体方法，通用的执行样例流程
    protected int executeTasks(List<Callable<String>> tasks, int point, int time_limit, List<String> outputList) throws InterruptedException, ExecutionException {
        // 执行任务并获取结果
        List<Future<String>> re = threadPool.invokeAll(tasks,time_limit);
        int len = re.size();
        for (int i = 0; i < len; i++) {
            Future<String> future = re.get(i);
            if (future.isCancelled()){
                return 0;
            }
            if (! future.get().equals(outputList.get(i))){
                return 0;
            }
        }

        return point;
    }
}
