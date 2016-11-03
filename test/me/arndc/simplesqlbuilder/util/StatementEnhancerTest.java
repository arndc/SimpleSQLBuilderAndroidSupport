package me.arndc.simplesqlbuilder.util;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.equalTo;

public class StatementEnhancerTest {
    @Test
    public void testTrimAStatementWithMultipleConsecutiveWhiteSpaces() throws Exception {
        // Assign
        String statement = " SELECT columnName  FROM     tableName   WHERE   columnName   =  'test';";
        String expectedStatement = " SELECT columnName FROM tableName WHERE columnName = 'test';";

        // Act
        String actualStatement = StatementEnhancer.trim(statement);

        // Assert
        MatcherAssert.assertThat(actualStatement, equalTo(expectedStatement));
    }

    @Test
    public void testEscapeAStringValue() throws Exception {
        // Assign
        String value = "test";
        String expectedValue = "'test'";

        // Act
        String actualValue = StatementEnhancer.escapeValue(value);

        // Assert
        MatcherAssert.assertThat(actualValue, equalTo(expectedValue));
    }

    @Test
    public void testEscapeAStringValueContainingAnApostrophe() throws Exception {
        // Assign
        String value = "test's";
        String expectedValue = "'test''s'";

        // Act
        String actualValue = StatementEnhancer.escapeValue(value);

        // Assert
        MatcherAssert.assertThat(actualValue, equalTo(expectedValue));
    }

    @Test
    public void testEscapeAIntegerValue() throws Exception {
        // Assign
        int value = 3;
        String expectedValue = "3";

        // Act
        String actualValue = StatementEnhancer.escapeValue(value);

        // Assert
        MatcherAssert.assertThat(actualValue, equalTo(expectedValue));
    }

    @Test
    public void testEscapeADateValue() throws Exception {
        // Assign
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2000, Calendar.DECEMBER, 31);

        Date value =  calendar.getTime();
        String expectedValue = "'2000-12-31'";

        // Act
        String actualValue = StatementEnhancer.escapeValue(value);

        // Assert
        MatcherAssert.assertThat(actualValue, equalTo(expectedValue));
    }

    @Test
    public void testEscapeACustomObject() throws Exception {
        // Assign
        class CustomObject {
            private String customText;

            private CustomObject(String customText) {
                this.customText = customText;
            }

            @Override
            public String toString() {
                return "customText=" + customText;
            }
        }

        CustomObject customObject = new CustomObject("custom");
        String expectedValue = "'customText=custom'";

        // Act
        String actualValue = StatementEnhancer.escapeValue(customObject);

        // Assert
        MatcherAssert.assertThat(actualValue, equalTo(expectedValue));
    }
}
