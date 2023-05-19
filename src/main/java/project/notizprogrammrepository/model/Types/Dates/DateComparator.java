package project.notizprogrammrepository.model.Types.Dates;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * A comparator class comparing 2 dates using date.compareTo(). Necessary because Comparator does not implement Serializable.
 */
public class DateComparator implements Comparator<Date>, Serializable {
    /**
     * A method comparing the 2 given Date objects using the compareTo method of the Date class.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return From the date.compareTo() documentation: "the value 0 if the argument Date is equal to this Date; a value less than 0 if this Date is before the Date argument; and a value greater than 0 if this Date is after the Date argument."
     */
    @Override
    public int compare(Date o1, Date o2) {
        return o1.compareTo(o2);
    }
}