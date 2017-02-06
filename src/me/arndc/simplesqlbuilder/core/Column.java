package me.arndc.simplesqlbuilder.core;

/**
 * This class represents a column in a database.
 */
public final class Column {

    private final String name;
    private final String dataType;

    private boolean primaryKey;
    private boolean autoIncrement;
    private boolean unique;
    private boolean notNull;

    public Column(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }


    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean hasPrimaryKey() {
        return primaryKey;
    }

    public boolean hasAutoIncrement() {
        return autoIncrement;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    String createDefinition() {
        String definition = String.format("%s %s", this.name, this.dataType);

        if (primaryKey) {
            definition += " PRIMARY KEY";
            if (autoIncrement)
                definition += " AUTOINCREMENT";
        } else if (unique) {
            definition += " UNIQUE";
        }

        if (notNull) {
            definition += " NOT NULL";
        }

        return definition;
    }


    /**
     * @param operator the condition that the column has to fulfill.
     * @return an operation that is particularly used in a where clause.
     */
    public String is(Operator operator) {
        return " " + this.name + operator.execute();
    }

}
