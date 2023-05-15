package project.notizprogrammrepository.model.Types.Dates;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date>, Serializable {
    @Override
    public int compare(Date o1, Date o2) {
        return o1.compareTo(o2);
    }
}