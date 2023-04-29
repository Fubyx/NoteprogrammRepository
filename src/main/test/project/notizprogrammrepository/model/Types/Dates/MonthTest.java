package project.notizprogrammrepository.model.Types.Dates;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MonthTest {

    @Test
    public void constructorTest(){
        Month m = new Month(2023, 4);
        assertNull(m.getDays()[0][0]);
        assertNotNull(m.getDays()[0][5]);
        assertNotNull(m.getDays()[4][6]);
        assertNull(m.getDays()[5][0]);
    }
}
