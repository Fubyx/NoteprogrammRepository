package project.notizprogrammrepository.model.Types.Dates;
import java.text.ParseException;
import java.util.Date;

/*

 */
//19.04.2023 Fabian: Constructor, isOnSameDay
public class Day {
    Date date;

    public Day(Date date) {
        this.date = date;
    }

    public void Day(Date d) throws ParseException {     //Verlangt, dass Alex ein Date übergibt.
        date = d;
    }

    public boolean isOnSameDay(Date date){
        return date.getYear() == this.date.getYear() && date.getMonth() == this.date.getMonth() && date.getDate() == this.date.getDate();
    }
}
