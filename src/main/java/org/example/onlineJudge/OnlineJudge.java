package org.example.onlineJudge;

import org.example.answer.Answer;
import org.example.answer.AnswerItem;
import org.example.exam.Exam;
import org.example.fileUtil.impl.CSVScoreWriter;
import org.example.question.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnlineJudge {
    //Map<Integer, Exam> idToExamMap = readExam(examsPath);
    ////        Map<Integer, Answer>
    //        List<Answer> answers = readAnswer(answersPath);
    private List<Answer> answerList;
    private Map<Integer, Exam> idToExamMap ;

    private List<ExamScore> examScoreList = new ArrayList<>();

    public OnlineJudge(List<Answer> answers, Map<Integer,Exam> examMap) {
        this.answerList = answers;
        this.idToExamMap = examMap;
    }

    public void calculateExamScoreList(){
        initQuestionScoreStrategy();
        // 使用 Lambda 表达式
        answerList.forEach(item -> {
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
    }

    public void saveExamScoreList() throws IOException {
        if (examScoreList.isEmpty()){
            return;
        }
        CSVScoreWriter csvScoreWriter = new CSVScoreWriter(examScoreList);
        csvScoreWriter.writeScore("src/test/resources/cases/"+"output.csv");
    }

    public int calculateExamScore(List<Question>questionList,List<AnswerItem> answers){
        int totalScore = 0;
        System.out.println(questionList.size()+"//"+answers.size());
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            if (question.getType() == 3){
                totalScore += question.getPoints();
                continue;
            }
            System.out.println(question.toString());
            int type = question.getType();
            String answer = answers.get(i).getAnswer();

//            int type = question.getType();
            totalScore += question.getScoringStrategy().calculateQuestionScore(convertTo(answer,type));
        }
        return totalScore;
    }

    private Object convertTo(String answer,int type){
        Object selfAnswer = null;
        switch (type){
            case 1://单选
                selfAnswer = (int)answer.charAt(0) - 65;
                break;
            case 2://多选
                List<Integer> list = new ArrayList<Integer>();
                for (char c : answer.toCharArray()) {
                    list.add((int) c - 65);
                }
                selfAnswer = list;
                break;
            case 3://编程题
                selfAnswer = answer;
                break;
            default:break;
        }
        return selfAnswer;
    }

    private  void initQuestionScoreStrategy(){
        for (Exam exam:idToExamMap.values()){
            exam.getQuestions().forEach(Question::initStrategy);
        }
    }

}
