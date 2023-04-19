package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

public abstract class Entry {
    private String title;
    private String text;
    private Date date;
    private double id;

    public Entry(String title, Date date, double id) {
        this.title = title;
        this.date = date;
        this.id = id;
    }
    public Entry(String title, String text, Date date, double id) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getId() {
        return id;
    }
    public void setId(double id) {
        this.id = id;
    }
}
