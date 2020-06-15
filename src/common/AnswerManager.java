package common;

import model.Question;
import util.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

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
     * 返回指定用户作答指定题目的答案
     *
     * @param questionID 指定的题目
     * @param username   指定的用户
     * @return 用户的作答
     * @throws SQLException
     */
    public static String getUserAnswer(int questionID, String username) throws SQLException {
        String result = null;
        String[] parameters = {username, String.valueOf(questionID)};
        ResultSet resultSet = Connect.executeSelect("SELECT * FROM Answer\n" +
                "WHERE `user`=?\n" +
                "AND questionID=?", parameters);
        if (resultSet.next()) {
            result = resultSet.getString("answer");
        }
        return result;
    }

    /**
     * 回答问题
     *
     * @param questionID 所回答问题的编号
     * @param responder  回答者的用户名
     * @param answer     所回答的答案
     */
    public static void answerQuestion(int questionID, String responder, String answer) throws SQLException {
        String[] parameters = {responder, String.valueOf(questionID), answer};
        if (UserManager.getUser(responder) == null ||
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
    public static String getCorrectRate(int questionID) throws SQLException {
        if (QuestionManager.getQuestion(questionID) == null) {
            return null;
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
                "WHERE Answer.questionID=Question.id\n" +
                "AND Answer.answer=Question.answer\n" +
                "AND questionID=?", parameters);
        if (resultSet.next()) {
            correctAnswer = resultSet.getInt("COUNT(*)");
        }
        return formatDouble(correctAnswer / totalAnswer);
    }

    /**
     * 返回浮点数保留两位的字符串形式
     *
     * @param s 浮点数
     * @return 保留两位的字符串
     */
    private static String formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("##0.00");
        return fmt.format(s);
    }
}
