package org.example.codeHandler.impl;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;
import org.example.codeHandler.Preprocessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaPreprocessor implements Preprocessor {

    private final String compileDirectory = "src/test/resources/cases/answers/bin/";

    public String getCompileDirectory() {
        return compileDirectory;
    }

    @Override
    public boolean compile(String filePath) {
//        System.out.println("正在编译.."  + filePath);
        try {
            // 构建编译命令
            ProcessBuilder processBuilder = new ProcessBuilder("javac", "-d", compileDirectory, filePath);
//            processBuilder.directory(new File(filePath).getParentFile()); // 设置工作目录为 Java 文件所在目录

            // 启动进程并等待其完成
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            process.destroy();
            if ( exitCode == 0){
//                System.out.println("编译成功");
                return true;
            }
//            System.out.println("编译结果exitCode: " + exitCode);
            // 返回编译结果
            return false;
        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
            return false; // 返回 -1 表示编译过程中出现异常
        }
    }

    @Override
    public int calculateMethodCyclomaticComplexity(Node method) {

        int complexity = 1 + calculateNodeComplexity(method);

        return complexity;
    }

    /**
     * 计算给定 Java 文件的类复杂度。
     * @define 定整个类的圈复杂度为其内部所有函数的圈复杂度之和
     * @param filePath 文件路径
     * @return 圈复杂度
     */
    @Override
    public int calculateClassCyclomaticComplexity(String filePath) {
        if (!compile(filePath)){
            return -1;
        }

        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JavaParser javaParser = new JavaParser();
        CompilationUnit parsedCompilationUnit = javaParser.parse(in).getResult().get();

        int totalComplexity = 0;

        for (MethodDeclaration method : parsedCompilationUnit.findAll(MethodDeclaration.class)) {
            totalComplexity += calculateNodeComplexity(method) + 1;
        }

        return totalComplexity;
    }

    private int calculateNodeComplexity(Node node) {
        int count = 0;

        if (node instanceof IfStmt || node instanceof WhileStmt || node instanceof DoStmt || node instanceof ForStmt || node instanceof ConditionalExpr) {
            count++;
        }

        if (node instanceof BinaryExpr) {
            BinaryExpr.Operator operator = ((BinaryExpr) node).getOperator();
            if (operator == BinaryExpr.Operator.AND || operator == BinaryExpr.Operator.OR) {
                count++;
            }
        }

        for (Node child : node.getChildNodes()) {
            count += calculateNodeComplexity(child);
        }

        return count;
    }

}
