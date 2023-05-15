package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

//17.04.2023 Fabian: Override equals
//24.04.2023 Fabian: Fix to constructor
//25.04.2023 Fabian: removed id, changed equals to compare date
public class Note extends Entry {
    private Subject subject;
    private boolean collectByTitle;
    public Note(String title, String text, Date date, boolean collectByTitle) {
        super(title, text, date);
        this.subject = Subject.NONE;
        this.collectByTitle = collectByTitle;
    }
    public Note(String title, String text, Date date, boolean collectByTitle, Subject subject) {
        super(title, text, date);
        this.subject = subject;
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
     * Compares the 2 Notes. Returns true only if the Date of obj matches the Date of the Note
     * @param obj The Note to compare to
     * @return Returns whether the Dates of the 2 Notes are equal.
     */
    @Override
    public boolean equals(Object obj){
        return ((obj instanceof Note) && (((Note) obj).getDate().equals(this.getDate())));
    }
}
