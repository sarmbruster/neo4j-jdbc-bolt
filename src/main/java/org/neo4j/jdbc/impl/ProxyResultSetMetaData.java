package org.neo4j.jdbc.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Stefan Armbruster
 */
public class ProxyResultSetMetaData implements ResultSetMetaData {

    private final ResultSetMetaData meta;

    public ProxyResultSetMetaData(ResultSetMetaData meta) {
        this.meta = meta;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return meta.getColumnCount();
    }

    @Override
    public boolean isAutoIncrement(int i) throws SQLException {
        return meta.isAutoIncrement(i);
    }

    @Override
    public boolean isCaseSensitive(int i) throws SQLException {
        return meta.isCaseSensitive(i);
    }

    @Override
    public boolean isSearchable(int i) throws SQLException {
        return meta.isSearchable(i);
    }

    @Override
    public boolean isCurrency(int i) throws SQLException {
        return meta.isCurrency(i);
    }

    @Override
    public int isNullable(int i) throws SQLException {
        return meta.isNullable(i);
    }

    @Override
    public boolean isSigned(int i) throws SQLException {
        return meta.isSigned(i);
    }

    @Override
    public int getColumnDisplaySize(int i) throws SQLException {
        return meta.getColumnDisplaySize(i);
    }

    @Override
    public String getColumnLabel(int i) throws SQLException {
        return meta.getColumnLabel(i);
    }

    @Override
    public String getColumnName(int i) throws SQLException {
        return meta.getColumnName(i);
    }

    @Override
    public String getSchemaName(int i) throws SQLException {
        return meta.getSchemaName(i);
    }

    @Override
    public int getPrecision(int i) throws SQLException {
        return meta.getPrecision(i);
    }

    @Override
    public int getScale(int i) throws SQLException {
        return meta.getScale(i);
    }

    @Override
    public String getTableName(int i) throws SQLException {
        return meta.getTableName(i);
    }

    @Override
    public String getCatalogName(int i) throws SQLException {
        return meta.getCatalogName(i);
    }

    @Override
    public int getColumnType(int i) throws SQLException {
        return meta.getColumnType(i);
    }

    @Override
    public String getColumnTypeName(int i) throws SQLException {
        return meta.getColumnTypeName(i);
    }

    @Override
    public boolean isReadOnly(int i) throws SQLException {
        return meta.isReadOnly(i);
    }

    @Override
    public boolean isWritable(int i) throws SQLException {
        return meta.isWritable(i);
    }

    @Override
    public boolean isDefinitelyWritable(int i) throws SQLException {
        return meta.isDefinitelyWritable(i);
    }

    @Override
    public String getColumnClassName(int i) throws SQLException {
        return meta.getColumnClassName(i);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return meta.unwrap(aClass);
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return meta.isWrapperFor(aClass);
    }
}
