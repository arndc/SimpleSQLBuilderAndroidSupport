package me.arndc.simplesqlbuilder.core;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.equalTo;

public class OperatorTest {
    @Test
    public void testOperatorBetweenTwoStrings() throws Exception {
        // Assign
        String val01 = "A", val02 = "B";
        String expected = String.format(" BETWEEN '%s' AND '%s'", val01, val02);

        // Act
        Operator operator = Operator.between(val01, val02);
        String actual = operator.execute();

        // Assert
        MatcherAssert.assertThat(actual, equalTo(expected));
    }

    @Test
    public void testOperatorBetweenTwoDates() throws Exception {
        // Assign
        Calendar calendar01 = GregorianCalendar.getInstance();
        calendar01.set(2016, Calendar.NOVEMBER, 1);

        Calendar calendar02 = GregorianCalendar.getInstance();
        calendar02.set(2017, Calendar.DECEMBER, 2);

        Date date01 = calendar01.getTime();
        Date date02 = calendar02.getTime();

        String expected = String.format(" BETWEEN '%s' AND '%s'", "2016-11-01", "2017-12-02");

        // Act
        Operator operator = Operator.between(date01, date02);
        String actual = operator.execute();

        // Assert
        MatcherAssert.assertThat(actual, equalTo(expected));
    }

    @Test
    public void testOperatorEqualsAString() throws Exception {
        // Assign
        String val01 = "A";
        String expected = String.format(" = '%s'", val01);

        // Act
        Operator operator = Operator.equalsTo(val01);
        String actual = operator.execute();

        // Assert
        MatcherAssert.assertThat(actual, equalTo(expected));
    }
}
