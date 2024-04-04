package org.example.codeHandler.impl;

import org.example.codeHandler.Compiler;

import java.io.IOException;

public class JavaCompiler implements Compiler {

    private final String compileDirectory = "src/test/resources/cases/answers/bin/";

    public String getCompileDirectory() {
        return compileDirectory;
    }

    @Override
    public boolean compile(String filePath) {
        System.out.println("正在编译.."  + filePath);
        try {
            // 构建编译命令
            ProcessBuilder processBuilder = new ProcessBuilder("javac", "-d", compileDirectory, filePath);
//            processBuilder.directory(new File(filePath).getParentFile()); // 设置工作目录为 Java 文件所在目录

            // 启动进程并等待其完成
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if ( exitCode == 0){
                System.out.println("编译成功");
                return true;
            }
            System.out.println("编译结果exitCode: " + exitCode);
            // 返回编译结果
            return false;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // 返回 -1 表示编译过程中出现异常
        }
    }

    public static void main(String[] args) {
        JavaCompiler javaCompiler = new JavaCompiler();
        javaCompiler.compile("src/test/resources/cases/answers/code-answers/Solution11.java");
    }
}
