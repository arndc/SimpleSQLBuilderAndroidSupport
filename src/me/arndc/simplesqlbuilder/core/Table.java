package me.arndc.simplesqlbuilder.core;

import me.arndc.simplesqlbuilder.util.Transformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static me.arndc.simplesqlbuilder.util.StatementEnhancer.trim;

/**
 * This class represents a table as known in a sql database.
 * This means it has a name and one or more columns.
 *
 * @see TableCommands
 * @see Column
 */
public final class Table implements TableCommands {
    private final String name;
    private Map<String, Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new LinkedHashMap<>();
    }

    public void addColumn(Column column) {
        this.columns.put(column.getName(), column);
    }

    public void addColumn(Column... columns) {
        Map<String, Column> newColumns = new LinkedHashMap<>();

        for (Column column : columns)
            newColumns.put(column.getName(), column);

        this.columns.putAll(newColumns);
    }

    public String getName() {
        return name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    @Override
    public String createStatement() {
        String statement = "CREATE TABLE " + this.name;

        List<CharSequence> definitions = new ArrayList<>();

        for (Column column : columns.values())
            definitions.add(column.createDefinition());


        statement += Transformer.joiner(definitions.toArray(new CharSequence[]{}), ", ", "(", ");");

        return trim(statement);
    }

    @Override
    public String dropStatement() {
        String statement = "DROP TABLE IF EXISTS " + this.name + ";";

        return trim(statement);
    }

}
