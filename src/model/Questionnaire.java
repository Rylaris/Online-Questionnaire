package model;

import java.util.ArrayList;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/12
 */
public class Questionnaire {
    private int id;
    private String description;
    private User maker;
    private ArrayList questions;

    public Questionnaire() {
    }

    public Questionnaire(int id, String description, User maker, ArrayList questions) {
        this.id = id;
        this.description = description;
        this.maker = maker;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public ArrayList getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList questions) {
        this.questions = questions;
    }

    public void addQuestions(Question newOption) {
        questions.add(newOption);
    }

    @Override
    public String toString() {
        String result = "";
        result += "问卷标题：" + description + "\n";
        result += "问卷ID：" + id + "\n";
        result += "出题人：" + maker.getUsername() + "\n";
        for (int i = 0; i < questions.size(); i++) {
            Question question = (Question) questions.get(i);
            result += question.toString();
        }
        return result;
    }
}
