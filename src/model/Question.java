package model;

import java.util.ArrayList;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/12
 */
public class Question {
    private int id;
    private String description;
    private String answer;
    private ArrayList options;

    public Question() {
    }

    public Question(int id, String description, String answer, ArrayList options) {
        this.id = id;
        this.description = description;
        this.answer = answer;
        this.options = options;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList getOptions() {
        return options;
    }

    public void setOptions(ArrayList options) {
        this.options = options;
    }

    public void addOptions(Option newOption) {
        options.add(newOption);
    }

    @Override
    public String toString() {
        String result = "";
        result += "问题描述：" + description + "\n";
        result += "问题ID：" + id + "\n";
        for (int i = 0; i < options.size(); i++) {
            Option option = (Option) options.get(i);
            result += option.toString();
        }
        result += "问题答案：" + answer + "\n";
        return result;
    }
}
