package me.arndc.simplesqlbuilder.builders;

import me.arndc.simplesqlbuilder.core.Column;
import me.arndc.simplesqlbuilder.core.Operator;
import me.arndc.simplesqlbuilder.core.Query;
import me.arndc.simplesqlbuilder.core.Table;
import me.arndc.simplesqlbuilder.matchers.QueryMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueryBuilderTest {
    private final static String testTableName = "test_table_name";
    private final static String testColumnName = "test_col_name";
    private final static String testWhereClause = testColumnName + " IS BETWEEN " + 5 + " AND " + 10;

    private static Table testTable;
    private static Column testColumn1;

    @Before
    public void setUp() throws Exception {
        testTable = new Table(testTableName);

        testColumn1 = new Column(testColumnName, "dummy_data_type");
    }

    @Test
    public void testBuildingAQueryWithSelectAll() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect("*");
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setWhereClause(testWhereClause);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectAll()
                                           .from(testTable.getName())
                                           .where(testWhereClause);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithOneColumn() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect(testColumnName);
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setWhereClause(testWhereClause);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .select(testColumnName)
                                           .from(testTable)
                                           .where(testWhereClause);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithMultipleColumns() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect(testColumn1.getName(), testColumn1.getName());
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setWhereClause(testWhereClause);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .select(testColumn1, testColumn1)
                                           .from(testTable)
                                           .where(testColumn1.is(Operator.between("one", "other")));

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithSelectDistinct() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect(testColumn1.getName(), testColumn1.getName());
        expectedQuery.setDistinct(true);
        expectedQuery.setFrom(testTable.getName());

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinct(testColumn1, testColumn1)
                                           .from(testTable);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithSelectDistinctAll() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect();
        expectedQuery.setDistinct(true);
        expectedQuery.setFrom(testTable.getName());

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinctAll()
                                           .from(testTable);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithOrderByAscending() throws Exception {
        // Arrange
        Query expectedQuery = new Query();
        expectedQuery.setSelect();
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setOrderBy(testColumn1.getName(), Query.Order.ASCENDING);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinctAll()
                                           .from(testTable)
                                           .orderBy(testColumn1, Query.Order.ASCENDING);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryByUsingTheWhereChainBuilder() throws Exception {
        // Arrange
        String val01 = "Value";

        Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(2016, Calendar.NOVEMBER, 1);

        Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.set(2017, Calendar.DECEMBER, 2);

        Date date01 = calendar1.getTime();
        Date date02 = calendar2.getTime();


        WhereChainBuilder whereChainBuilder = WhereChainBuilder.whereChain(testColumn1.is(Operator.equalsTo(val01)))
                                                               .and(testColumn1.is(Operator.between(date01, date02)));

        Query expectedQuery = new Query();
        expectedQuery.setSelect();
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setWhereClause(whereChainBuilder.end());

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinctAll()
                                           .from(testTable)
                                           .where(whereChainBuilder);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithLimit() throws Exception {
        // Assign
        long limit = 5;

        Query expectedQuery = new Query();
        expectedQuery.setSelect();
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setLimit(limit);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinctAll()
                                           .from(testTable)
                                           .limit(5);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }

    @Test
    public void testBuildingAQueryWithLimitAndOffset() throws Exception {
        // Assign
        long limit = 5;
        long offset = 1;

        Query expectedQuery = new Query();
        expectedQuery.setSelect();
        expectedQuery.setFrom(testTable.getName());
        expectedQuery.setLimit(limit);
        expectedQuery.setOffset(offset);

        // Act
        QueryBuilder builder = QueryBuilder.newQuery()
                                           .selectDistinctAll()
                                           .from(testTable)
                                           .limit(5, 1);

        Query query = builder.build();

        // Assert
        assertThat(query, is(QueryMatcher.isQuery(expectedQuery)));
    }
}
