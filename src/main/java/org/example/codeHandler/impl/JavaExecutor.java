package org.example.codeHandler.impl;

import org.example.codeHandler.Executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaExecutor implements Executor {
    @Override
    public String execute(String compiledFilePath, String[] args) {
//        System.out.println("执行中");
        try {
            // 获取最后一个斜杠的索引位置
            int lastIndex = compiledFilePath.lastIndexOf("/");

            // 获取父目录路径（src/test/resources/cases/answers/bin）
            String directoryPath = compiledFilePath.substring(0, lastIndex);
//            System.out.println("directoryPath: "+directoryPath);

            String className = compiledFilePath.substring(lastIndex + 1, compiledFilePath.lastIndexOf("."));
//            System.out.println("className: "+className);
            // 构建执行命令
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add(className);
            command.addAll(Arrays.asList(args));

            // 构建进程
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new java.io.File(directoryPath));

            // 启动进程并等待其完成
            Process process = processBuilder.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder re = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                re.append(line);
//                System.out.println(line);
            }

            // 读取错误输出
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            while ((line = errorReader.readLine()) != null) {
//                System.err.println(line);
//            }

            int exitCode = process.waitFor();
//            System.out.println("执行结果exitCode: " + exitCode);

            // 如果exitCode不为0，则抛出异常
            if (exitCode != 0) {
                throw new RuntimeException("Execution failed with exit code: " + exitCode);
            }
            // 返回编译结果
            return re.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Execution failed");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JavaExecutor javaExecutor = new JavaExecutor();
        String[] temp = {"1","3"};
        javaExecutor.execute("src/test/resources/cases/answers/bin/Solution11", temp );
    }
}
