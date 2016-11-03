package me.arndc.simplesqlbuilder.core;

import me.arndc.simplesqlbuilder.util.Transformer;

/**
 * This class provides only static methods for doing arithmetic operations on columns.
 * These operations can be used inside a {@link Function}.
 *
 * @see Function
 */
public final class ArithmeticColumnOperation {
    public static String multiply(Column... columns) {
        return multiply(Transformer.columnsToColumnNames(columns));
    }

    public static String multiply(CharSequence... columnNames) {
        return Transformer.joiner(columnNames, " * ");
    }
}
