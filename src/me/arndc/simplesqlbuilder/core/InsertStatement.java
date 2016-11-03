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
    private Map<String, Object> values;

    public InsertStatement(String tableName) {
        this.tableName = tableName;
        values = new LinkedHashMap<>();
    }

    public void addValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public String statement() {
        List<String> valueList = new ArrayList<>();

        for (Object o : values.values())
            valueList.add(StatementEnhancer.escapeValue(o));

        String columns = Transformer.joiner(values.keySet(), ", ", "(", ")");
        String values = Transformer.joiner(valueList, ", ", "(", ")");

        String statement = "INSERT INTO " + tableName + " " + columns + " VALUES " + values + ";";
        return trim(statement);
    }
}