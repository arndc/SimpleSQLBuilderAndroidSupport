package me.arndc.simplesqlbuilder.core;

import me.arndc.simplesqlbuilder.util.StatementEnhancer;
import me.arndc.simplesqlbuilder.util.Transformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static me.arndc.simplesqlbuilder.util.StatementEnhancer.trim;

/**
 * This class represents the parts of an sql insert statement.
 *
 * @see Statement
 */
public final class InsertStatement implements Statement {
    private String tableName;
    private Map<String, List<Object>> insertMap;
    private boolean ignore;

    public InsertStatement(String tableName) {
        this.tableName = tableName;
        this.insertMap = new LinkedHashMap<>();
        this.ignore = false;
    }

    public void addValue(String columnName, Object value) {
        if (!insertMap.containsKey(columnName))
            insertMap.put(columnName, new ArrayList<>());

        insertMap.get(columnName).add(value);
    }

    public void setColumnNames(CharSequence... columnNames) {
        for (CharSequence columnName : columnNames)
            if (!insertMap.containsKey((String) columnName))
                insertMap.put((String) columnName, new ArrayList<>());
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, List<Object>> getInsertMap() {
        return insertMap;
    }

    @Override
    public String statement() {

        String columns = Transformer.joiner(insertMap.keySet(), ", ", "(", ")");

        StringBuilder statement = new StringBuilder("INSERT");

        if (ignore)
            statement.append(" OR IGNORE ");

        statement.append(" INTO ")
                 .append(tableName)
                 .append(" ")
                 .append(columns);

        int numberOfValuesToAdd = insertMap.get(insertMap.keySet().iterator().next()).size();

        statement.append(" VALUES ");

        for (int i = 0; i < numberOfValuesToAdd; i++) {
            List<String> enhancedValues = new ArrayList<>();

            for (String key : insertMap.keySet())
                enhancedValues.add(StatementEnhancer.escapeValue(insertMap.get(key).get(i)));

            statement.append(Transformer.joiner(enhancedValues, ", ", "(", ")"));

            if (i < (numberOfValuesToAdd - 1))
                statement.append(", ");
        }

        statement.append(";");

        return trim(statement.toString());
    }
}