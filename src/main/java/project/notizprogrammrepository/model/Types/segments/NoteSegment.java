package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.List;

public class NoteSegment extends CalendarSegment{
    private List<NoteCollection> collections;
    public void removeAll(Day day){}
    public void collectBySubject(Note note){}
    public void collectByTitle(Note note){

    }
}
