package org.example.onlineJudge;

import org.example.codeHandler.ProcessorTemplate;
import org.example.codeHandler.impl.JavaCodeProcessor;
import org.example.entity.*;
import org.example.fileUtil.FileProcessor;
import org.example.fileUtil.impl.CSVScoreWriter;
import org.example.question.ProgrammingQuestion;
import org.example.question.Question;
import org.example.threadPool.ThreadPool;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

public class OnlineJudge {
    private List<Answer> answerList;
    private Map<Integer, Exam> idToExamMap;

    private List<ExamScore> examScoreList = new ArrayList<>();
    private List<Complexity> codeComplexityList = new ArrayList<>();

    private FileProcessor fileProcessor;

    private String examsPath;
    private String answersPath;
    private String outPath;
    public OnlineJudge(String examsPath, String answersPath, String outPath) {
        this.fileProcessor = new FileProcessor();
        this.examsPath = examsPath;
        this.answersPath = answersPath;
        this.outPath = outPath;
    }

    public void run() throws IOException {

        if (outPath.endsWith("output.csv")){
            init(examsPath,answersPath);
            calculateExamScoreList();
            ThreadPool.getInstance().shutdown();
            saveScoreList(outPath);
        }else {
            runComplexity();
        }

    }

    public void runComplexity() throws IOException {
        this.answerList = fileProcessor.readAnswer(answersPath);
        calculateComplexityList();
        saveComplexityList(outPath);
    }

    public void init(String examsPath, String answersPath ) throws IOException {
        this.answerList = fileProcessor.readAnswer(answersPath);
        this.idToExamMap = fileProcessor.readExam(examsPath);
        initQuestionScoreStrategy();
    }

//    public void
    public List<ExamScore> calculateExamScoreList(){
        // 使用 Lambda 表达式
        answerList.forEach(item -> {
//            System.out.println(item.toString());
            // 在这里对每个元素执行操作
            Exam exam = idToExamMap.get(item.getExamId());
            //判断是不是超过提交时间或者提前教
            long submit_time = item.getSubmitTime();

            if (exam.getStartTime() > submit_time || exam.getEndTime() < submit_time){
                ExamScore examScore = new ExamScore(item.getExamId(),item.getStudentId(),0);
                examScoreList.add(examScore);
                return;
            }

            List<Question>questionList = exam.getQuestions();
            List<AnswerItem> answers = item.getAnswers();

            int score = calculateExamScore(questionList,answers);
            ExamScore examScore = new ExamScore(item.getExamId(),item.getStudentId(),score);
            examScoreList.add(examScore);
        });

        return examScoreList;
    }

    public int calculateExamScore(List<Question>questionList,List<AnswerItem> answers){
        int totalScore = 0;
        int gradeOfPerQuestion = 0;
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            String answer = answers.get(i).getAnswer();
            gradeOfPerQuestion = question.getScoringStrategy().calculateQuestionScore(answer);
            if (question instanceof ProgrammingQuestion){

            }
            totalScore += gradeOfPerQuestion;
        }
        return totalScore;
    }

    public void saveScoreList(String outputPath) throws IOException {
        fileProcessor.saveExamScoreList(outputPath,examScoreList);
    }

    public void saveComplexityList(String outputPath) throws IOException {
        fileProcessor.saveComplexityList(outputPath,codeComplexityList);
    }

    private  void initQuestionScoreStrategy(){
        for (Exam exam:idToExamMap.values()){
            exam.getQuestions().forEach(Question::initStrategy);
        }
    }

    private void calculateComplexityList(){
        JavaCodeProcessor codeProcessor = new JavaCodeProcessor();
        // 使用 Lambda 表达式
        answerList.forEach(item -> {
//            System.out.println(item.toString());
            // 在这里对每个元素执行操作

            item.getAnswers().forEach(answersItem -> {

                if (answersItem.getId() == 3){
                    Complexity codeComplexity =  new Complexity(item.getExamId(),item.getStudentId(),3,
                            codeProcessor.getCyclomaticComplexity("src/test/resources/cases/answers/" + answersItem.getAnswer()));
                    codeComplexityList.add(codeComplexity);
                }
            });
        });
    }

}
