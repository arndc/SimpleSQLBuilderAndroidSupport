package me.arndc.simplesqlbuilder.builders;

import me.arndc.simplesqlbuilder.core.Column;
import me.arndc.simplesqlbuilder.core.InsertStatement;
import me.arndc.simplesqlbuilder.core.Table;
import me.arndc.simplesqlbuilder.util.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * This builder class provides a fluent way to build an {@link InsertStatement}.
 *
 * @see InsertStatement
 */
public final class InsertStatementBuilder {
    private InsertStatement insertStatement;
    private CharSequence[] columnNames;
    private List<Object[]> values;

    private InsertStatementBuilder(String tableName) {
        insertStatement = new InsertStatement(tableName);
        values = new ArrayList<>();
    }

    public static InsertStatementBuilder insertInto(String tableName) {
        return new InsertStatementBuilder(tableName);
    }

    public static InsertStatementBuilder insertInto(Table table) {
        return insertInto(table.getName());
    }

    @Deprecated
    public InsertStatementBuilder withValue(String columnName, Object value) {
        insertStatement.addValue(columnName, value);
        return this;
    }

    @Deprecated
    public InsertStatementBuilder withValue(Column column, Object value) {
        return withValue(column.getName(), value);
    }

    public InsertStatementBuilder inColumns(CharSequence... columnNames) {
        this.columnNames = columnNames;
        insertStatement.setColumnNames(columnNames);
        return this;
    }

    public InsertStatementBuilder inColumns(Column... columns) {
        return inColumns(Transformer.columnsToColumnNames(columns));
    }

    public InsertStatementBuilder withValues(Object... values) {
        this.values.add(values);
        return this;
    }

    public InsertStatementBuilder ignoreDuplicates() {
        this.insertStatement.setIgnore(true);
        return this;
    }

    public InsertStatement build() {
        for (Object[] value : values)
            for (int i = 0; i < columnNames.length; i++)
                insertStatement.addValue((String) columnNames[i], value[i]);

        return insertStatement;
    }

    public String buildStatement() {
        return build().statement();
    }

}