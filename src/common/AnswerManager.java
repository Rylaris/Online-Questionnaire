package common;

import model.Question;
import model.User;
import util.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/13
 */
public class AnswerManager {
    /**
     * 返回给定题目的答案
     *
     * @param questionID 题目编号
     * @return 返回答案的字符串表示形式
     */
    public static String getAnswer(int questionID) throws SQLException {
        Question target = QuestionManager.getQuestion(questionID);
        if (target == null) {
            return null;
        } else {
            return target.getAnswer();
        }
    }

    /**
     * 回答问题
     *
     * @param questionID 所回答问题的编号
     * @param responder  回答者
     * @param answer     所回答的答案
     */
    public static void answerQuestion(int questionID, User responder, String answer) throws SQLException {
        String[] parameters = {responder.getUsername(), String.valueOf(questionID), answer};
        if (UserManager.getUser(responder.getUsername()) == null ||
                QuestionManager.getQuestion(questionID) == null) {
            return;
        }
        Connect.executeUpdate("INSERT INTO `Answer`(`user`, `questionID`, `answer`) VALUES (?,?,?)", parameters);
    }

    /**
     * 返回给定题目的正确率
     *
     * @param questionID 给定的问题编号
     * @return 返回正确率，大于等于0，小于等于1
     */
    public static double getCorrectRate(int questionID) throws SQLException {
        if (QuestionManager.getQuestion(questionID) == null) {
            return -1;
        }
        String[] parameters = {String.valueOf(questionID)};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*)\n" +
                "FROM Answer\n" +
                "WHERE Answer.questionID=?", parameters);
        double totalAnswer = 1;
        double correctAnswer = 0;
        if (resultSet.next()) {
            totalAnswer = resultSet.getInt("COUNT(*)");
        }
        resultSet = Connect.executeSelect("SELECT COUNT(*)\n" +
                "FROM Answer, Question\n" +
                "WHERE Answer.questionID=?\n" +
                "AND Answer.answer=Question.answer", parameters);
        if (resultSet.next()) {
            correctAnswer = resultSet.getInt("COUNT(*)");
        }
        return correctAnswer / totalAnswer;
    }
}
