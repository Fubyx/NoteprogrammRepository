package project.notizprogrammrepository.model.Types.entries;

import java.io.Serializable;
import java.util.Date;

public abstract class Entry implements Serializable {
    private String title;
    private String text;
    private Date date;

    public Entry(String title){
        this.title = title;
    }
    public Entry(String title, Date date) {
        this.title = title;
        this.date = date;
    }
    public Entry(String title, String text, Date date) {
        this.title = title;
        this.text = text;
        this.date = date;
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
}
