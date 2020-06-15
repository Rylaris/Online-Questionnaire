package test;

import java.sql.*;
import java.util.ArrayList;

import common.AnswerManager;
import common.QuestionManager;
import common.UserManager;
import model.Option;
import model.Question;
import model.Questionnaire;
import model.User;

/**
 * @author 软工1801温蟾圆
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        User test = UserManager.getUser("温蟾圆");
//        User test1 = new User("温蟾圆", "123", true);
//        UserManager.addUser(test1);
//        Questionnaire questionnaire = new Questionnaire();
//        questionnaire.setId(1);
//        questionnaire.setDescription("天文学知识小测试");
//        questionnaire.setMaker(UserManager.getUser("温蟾圆"));
//        questionnaire.setQuestions(new ArrayList());
//        Question question = new Question();
//        question.setId(1);
//        question.setAnswer("D");
//        question.setDescription("海王星的英文是什么？");
//        question.setOptions(new ArrayList());
//        question.addOptions(new Option(1, "Jupiter"));
//        question.addOptions(new Option(2, "Saturn"));
//        question.addOptions(new Option(3, "Uranus"));
//        question.addOptions(new Option(4, "Neptune"));
//        questionnaire.addQuestions(question);
//        QuestionManager.addQuestionnaire(questionnaire);
//        Questionnaire test = QuestionManager.getQuestionnaire(1);

        System.out.print(QuestionManager.getAllOptionNum());
        return;
    }
}
