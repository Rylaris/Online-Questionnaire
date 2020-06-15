package common;

import model.*;
import util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/12
 */
public class QuestionManager {
    /**
     * 返回给定的题目编号的选项个数
     *
     * @param questionID 题目编号
     * @return 选项个数
     */
    public static int getOptionNum(int questionID) throws SQLException {
        String[] parameters = {String.valueOf(questionID)};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*) FROM OQ, `Option`\n" +
                "WHERE OQ.optionID=`Option`.id\n" +
                "AND OQ.questionID=?", parameters);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;
    }

    /**
     * 返回给定的问卷编号的题目个数
     *
     * @param questionnaireID 问卷编号
     * @return 题目个数
     */
    public static int getQuestionNum(int questionnaireID) throws SQLException {
        String[] parameters = {String.valueOf(questionnaireID)};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*) FROM QQ, `Question`\n" +
                "WHERE QQ.questionID=`Question`.id\n" +
                "AND QQ.questionnaireID=?", parameters);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;
    }

    /**
     * @return 返回数据库中问卷的数量，用于确定新问卷的ID
     */
    public static int getQuestionnaireNum() throws SQLException {
        String[] parameter = {};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*)\n" +
                "        FROM Questionnaire", parameter);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;
    }

    /**
     * @return 返回数据库中问题的数量，用于确定新问题的ID
     */
    public static int getAllQuestionNum() throws SQLException {
        String[] parameter = {};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*)\n" +
                "        FROM Question", parameter);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;
    }

    /**
     * @return 返回数据库中选项的数量，用于确定新选项的ID
     */
    public static int getAllOptionNum() throws SQLException {
        String[] parameter = {};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*) FROM `Option`", parameter);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;
    }

    /**
     * 返回给定编号的选项
     *
     * @param id 选项编号
     * @return 返回该选项的详细信息
     */
    public static Option getOption(int id) throws SQLException {
        Option result = null;
        String[] parameters = {String.valueOf(id)};
        ResultSet resultSet = Connect.executeSelect("SELECT * FROM `Option`\n" +
                "WHERE `Option`.id=?", parameters);
        if (resultSet.next()) {
            result = new Option();
            result.setId(resultSet.getInt("id"));
            result.setDescription(resultSet.getString("description"));
        }
        return result;
    }

    /**
     * 返回给定编号的题目
     *
     * @param id 题目编号
     * @return 返回该题目的详细信息
     */
    public static Question getQuestion(int id) throws SQLException {
        Question result = null;
        String[] parameters = {String.valueOf(id)};
        ResultSet resultSet = Connect.executeSelect("SELECT * FROM `Question`\n" +
                "WHERE `Question`.id=?", parameters);
        if (resultSet.next()) {
            result = new Question();
            result.setId(resultSet.getInt("id"));
            result.setDescription(resultSet.getString("description"));
            result.setAnswer(resultSet.getString("answer"));
            result.setOptions(new ArrayList());
        }
        if (result != null) {
            parameters = new String[]{String.valueOf(result.getId())};
            resultSet = Connect.executeSelect("SELECT * FROM `OQ`\n" +
                    "WHERE questionID=?\n" +
                    "ORDER BY `order`", parameters);
            while (resultSet.next()) {
                result.addOptions(getOption(resultSet.getInt("optionID")));
            }
        }
        return result;
    }

    /**
     * 返回给定编号的问卷
     *
     * @param id 问卷编号
     * @return 返回该问卷的详细信息
     */
    public static Questionnaire getQuestionnaire(int id) throws SQLException {
        Questionnaire result = null;
        String[] parameters = {String.valueOf(id)};
        ResultSet resultSet = Connect.executeSelect("SELECT * FROM `Questionnaire`\n" +
                "WHERE `Questionnaire`.id=?", parameters);
        if (resultSet.next()) {
            result = new Questionnaire();
            result.setId(resultSet.getInt("id"));
            result.setDescription(resultSet.getString("description"));
            result.setMaker(UserManager.getUser(resultSet.getString("maker")));
            result.setQuestions(new ArrayList());
        }
        if (result != null) {
            parameters = new String[]{String.valueOf(result.getId())};
            resultSet = Connect.executeSelect("SELECT * FROM `QQ`\n" +
                    "WHERE questionnaireID=?\n" +
                    "ORDER BY `order`", parameters);
            while (resultSet.next()) {
                result.addQuestions(getQuestion(resultSet.getInt("questionID")));
            }
        }
        return result;
    }

    /**
     * 将新的选项插入数据库
     *
     * @param newOption 待插入的选项
     */
    public static void addOption(Option newOption) throws SQLException {
        int id = getAllOptionNum() + 1;
        String description = newOption.getDescription();
        String[] parameters = {String.valueOf(id), description};
        if (getOption(id) != null) {
            return;
        }
        Connect.executeUpdate("INSERT INTO `Option`(id,description) VALUES (?, ?)", parameters);
    }

    /**
     * 将新的题目插入数据库，同时维护选项与题目的关系
     *
     * @param newQuestion 待插入的题目
     */
    public static void addQuestion(Question newQuestion) throws SQLException {
        int id = getAllQuestionNum() + 1;
        String description = newQuestion.getDescription();
        String answer = newQuestion.getAnswer();
        String[] parameters = {String.valueOf(id), description, answer};
        if (getQuestion(id) != null) {
            return;
        }
        Connect.executeUpdate("INSERT INTO `Question`(id,description,answer) VALUES (?, ?, ?)", parameters);
        for (int i = 0; i < newQuestion.getOptions().size(); i++) {
            Option newOption = (Option) newQuestion.getOptions().get(i);
            parameters = new String[]{String.valueOf(id), String.valueOf(getAllOptionNum() + 1), String.valueOf(i + 1)};
            addOption(newOption);
            Connect.executeUpdate("INSERT INTO `OQ`(`questionID`,`optionID`,`order`) VALUES (?, ?, ?)", parameters);
        }
    }

    /**
     * 将新的问卷插入数据库，同时维护问题与问卷的关系
     *
     * @param newQuestionnaire 待插入的问卷
     */
    public static void addQuestionnaire(Questionnaire newQuestionnaire) throws SQLException {
        int id = getQuestionnaireNum() + 1;
        String description = newQuestionnaire.getDescription();
        User maker = newQuestionnaire.getMaker();
        if (!maker.isAdministrator()) {
            return;
        }
        String[] parameters = {String.valueOf(id), description, maker.getUsername()};
        if (getQuestionnaire(id) != null) {
            return;
        }
        Connect.executeUpdate("INSERT INTO `Questionnaire`(`id`,`description`,`maker`) VALUES (?, ?, ?)", parameters);
        for (int i = 0; i < newQuestionnaire.getQuestions().size(); i++) {
            Question newQuestion = (Question) newQuestionnaire.getQuestions().get(i);
            parameters = new String[]{String.valueOf(id), String.valueOf(getAllQuestionNum() + 1), String.valueOf(i + 1)};
            addQuestion(newQuestion);
            Connect.executeUpdate("INSERT INTO `QQ`(`questionnaireID`,`questionID`,`order`) VALUES (?, ?, ?)", parameters);
        }
    }

    public static int questionNumMakeByUser(String username) throws SQLException {
        String[] parameters = {username};
        ResultSet resultSet = Connect.executeSelect("SELECT COUNT(*)\n" +
                "        FROM Questionnaire, QQ\n" +
                "        WHERE Questionnaire.id=QQ.questionnaireID\n" +
                "        AND maker=?", parameters);
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("COUNT(*)");
        }
        return result;

    }
}
