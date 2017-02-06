package me.arndc.simplesqlbuilder.util;

import me.arndc.simplesqlbuilder.core.Column;

import java.util.Collection;

public class Transformer {
    private Transformer() {
    }

    public static CharSequence[] columnsToColumnNames(Column[] columns) {
        CharSequence[] columnNames = new String[columns.length];

        for (int i = 0; i < columns.length; i++)
            columnNames[i] = columns[i].getName();

        return columnNames;
    }

    public static String joiner(CharSequence[] values, String delimiter) {
        StringBuilder joinedString = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            joinedString.append(values[i]);

            if (i < (values.length - 1))
                joinedString.append(delimiter);
        }

        return joinedString.toString();
    }


    public static String joiner(CharSequence[] values, String delimiter, String prefix, String suffix) {
        return prefix + joiner(values, delimiter) + suffix;
    }

    public static String joiner(Collection<String> values, String delimiter) {
        return joiner(values.toArray(new CharSequence[]{}), delimiter);
    }

    public static String joiner(Collection<String> values, String delimiter, String prefix, String suffix) {
        return prefix + joiner(values, delimiter) + suffix;
    }
}
