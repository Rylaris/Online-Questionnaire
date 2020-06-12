package test;

import java.sql.*;
import java.util.ArrayList;

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
//        User test = UserManager.getUser("温蟾圆");
//        User test1 = new User("小敏", "123", true);
//        UserManager.addUser(test1);
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(2);
        questionnaire.setDescription("test");
        questionnaire.setMaker(UserManager.getUser("温蟾圆"));
        questionnaire.setQuestions(new ArrayList());
        Question question = new Question();
        question.setId(3);
        question.setAnswer("D");
        question.setDescription("海王星的英文是什么？");
        question.setOptions(new ArrayList());
        question.addOptions(new Option(9, "Jupiter"));
        question.addOptions(new Option(10, "Saturn"));
        question.addOptions(new Option(11, "Uranus"));
        question.addOptions(new Option(12, "Neptune"));
        questionnaire.addQuestions(question);
        Questionnaire test = QuestionManager.getQuestionnaire(1);
//        System.out.print(QuestionManager.getQuestionnaire(1));
        return;
    }
}
