package project.notizprogrammrepository.model.Types.Dates;
import java.text.ParseException;
import java.util.Date;

/*

 */

public class Day {
    Date date = new Date();

    public void Day(Date d) throws ParseException {     //Verlangt, dass Alex ein Date übergibt.
        date = d;
    }
}
