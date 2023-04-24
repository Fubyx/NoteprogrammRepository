package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;
import java.util.Objects;

//17.04.2023 Fabian: Override equals
//24.04.2023 Fabian: Fix to constructor
public class Note extends Entry{
    private Subject subject;
    private boolean collectByTitle;
    public Note(String title, String text, Date date, double id, boolean collectByTitle) {
        super(title, text, date, id);
        this.subject = Subject.NONE;
        this.collectByTitle = collectByTitle;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public boolean isCollectByTitle() {
        return collectByTitle;
    }
    public void setCollectByTitle(boolean collectByTitle) {
        this.collectByTitle = collectByTitle;
    }
    /**
     * Compares the 2 Notes. Returns true only if the id of obj matches the id of the Note
     * @param obj The Note to compare to
     * @return Returns whether the ids of the 2 Notes are equal.
     */
    @Override
    public boolean equals(Object obj){
        return ((obj instanceof Note) && (((Note) obj).getId() == this.getId()));
    }
}
