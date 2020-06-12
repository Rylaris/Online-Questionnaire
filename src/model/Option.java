package model;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/12
 */
public class Option {
    private int id;
    private String description;

    public Option() {}

    public Option(int id, String description) {
        this.id = id;
        this.description = description;
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
}
