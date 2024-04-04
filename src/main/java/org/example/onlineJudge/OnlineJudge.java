package org.example.onlineJudge;

import org.example.entity.Answer;
import org.example.entity.AnswerItem;
import org.example.entity.Exam;
import org.example.entity.ExamScore;
import org.example.fileUtil.FileProcessor;
import org.example.fileUtil.impl.CSVScoreWriter;
import org.example.question.Question;
import org.example.threadPool.ThreadPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class OnlineJudge {
    private List<Answer> answerList;
    private Map<Integer, Exam> idToExamMap;

    private List<ExamScore> examScoreList = new ArrayList<>();

    private FileProcessor fileProcessor;

    private // 创建一个线程池
    ThreadPool threadPool = new ThreadPool(5);

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
        init(examsPath,answersPath);
        calculateExamScoreList();
        saveScoreList(outPath);
    }

    public void init(String examsPath, String answersPath ) throws IOException {
        this.answerList = fileProcessor.readAnswer(answersPath);
        this.idToExamMap = fileProcessor.readExam(examsPath);
        initQuestionScoreStrategy();
    }

    public List<ExamScore> calculateExamScoreList(){
        // 使用 Lambda 表达式
        answerList.forEach(item -> {
            System.out.println(item.toString());
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
        threadPool.shutdown();
        return examScoreList;
    }

    public int calculateExamScore(List<Question>questionList,List<AnswerItem> answers){
        int totalScore = 0;
        int gradeOfPerQuestion = 0;
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            String answer = answers.get(i).getAnswer();

            // 创建一个任务并提交给线程池
            Future<Integer> future = threadPool.submit(() -> {
                // System.out.println("Current thread: " + Thread.currentThread().getName());
                return question.getScoringStrategy().calculateQuestionScore(answer);
            });

            // 获取任务的结果，也就是分数
            try {
                gradeOfPerQuestion = future.get();
            } catch (Exception e) {
                gradeOfPerQuestion = 0;
                e.printStackTrace();
            }
            totalScore += gradeOfPerQuestion;
        }
        return totalScore;
    }

    public void saveScoreList(String outputPath) throws IOException {
        fileProcessor.saveExamScoreList(outputPath,examScoreList);
    }

    private  void initQuestionScoreStrategy(){
        for (Exam exam:idToExamMap.values()){
            exam.getQuestions().forEach(Question::initStrategy);
        }
    }

}
