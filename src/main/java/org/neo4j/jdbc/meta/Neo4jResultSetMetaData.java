/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 * <p/>
 * This file is part of Neo4j.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.jdbc.meta;

import org.neo4j.driver.Result;
import org.neo4j.driver.ReusableResult;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.util.Iterables;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.*;

public class Neo4jResultSetMetaData implements ResultSetMetaData {

    // TODO: replace with JDK 8's https://docs.oracle.com/javase/8/docs/api/java/sql/JDBCType.html 
    static final Map<Integer, String> JDBC_TYPE_MAP = new HashMap<>();
    static {
        JDBC_TYPE_MAP.put(BIT, "BIT");
        JDBC_TYPE_MAP.put(TINYINT, "TINYINT");
        JDBC_TYPE_MAP.put(SMALLINT, "SMALLINT");
        JDBC_TYPE_MAP.put(INTEGER, "INTEGER");
        JDBC_TYPE_MAP.put(BIGINT, "BIGINT");
        JDBC_TYPE_MAP.put(FLOAT, "FLOAT");
        JDBC_TYPE_MAP.put(REAL, "REAL");
        JDBC_TYPE_MAP.put(DOUBLE, "DOUBLE");
        JDBC_TYPE_MAP.put(NUMERIC, "NUMERIC");
        JDBC_TYPE_MAP.put(DECIMAL, "DECIMAL");
        JDBC_TYPE_MAP.put(CHAR, "CHAR");
        JDBC_TYPE_MAP.put(VARCHAR, "VARCHAR");
        JDBC_TYPE_MAP.put(LONGVARCHAR, "LONGVARCHAR");
        JDBC_TYPE_MAP.put(DATE, "DATE");
        JDBC_TYPE_MAP.put(TIME, "TIME");
        JDBC_TYPE_MAP.put(TIMESTAMP, "TIMESTAMP");
        JDBC_TYPE_MAP.put(BINARY, "BINARY");
        JDBC_TYPE_MAP.put(VARBINARY, "VARBINARY");
        JDBC_TYPE_MAP.put(LONGVARBINARY, "LONGVARBINARY");
        JDBC_TYPE_MAP.put(NULL, "NULL");
        JDBC_TYPE_MAP.put(OTHER, "OTHER");
        JDBC_TYPE_MAP.put(JAVA_OBJECT, "JAVA_OBJECT");
        JDBC_TYPE_MAP.put(DISTINCT, "DISTINCT");
        JDBC_TYPE_MAP.put(STRUCT, "STRUCT");
        JDBC_TYPE_MAP.put(ARRAY, "ARRAY");
        JDBC_TYPE_MAP.put(BLOB, "BLOB");
        JDBC_TYPE_MAP.put(CLOB, "CLOB");
        JDBC_TYPE_MAP.put(REF, "REF");
        JDBC_TYPE_MAP.put(DATALINK, "DATALINK");
        JDBC_TYPE_MAP.put(BOOLEAN, "BOOLEAN");
        JDBC_TYPE_MAP.put(ROWID, "ROWID");
        JDBC_TYPE_MAP.put(NCHAR, "NCHAR");
        JDBC_TYPE_MAP.put(NVARCHAR, "NVARCHAR");
        JDBC_TYPE_MAP.put(LONGNVARCHAR, "LONGNVARCHAR");
        JDBC_TYPE_MAP.put(NCLOB, "NCLOB");
        JDBC_TYPE_MAP.put(SQLXML, "SQLXML");
    }

    private final Result result;
    private final List<String> fieldNames;
    private ReusableResult retainedResult;

    public Neo4jResultSetMetaData(Result result) {
        this.result = result;
        this.fieldNames = Iterables.toList(result.fieldNames());
    }

    @Override
    public int getColumnCount() throws SQLException {
        return fieldNames.size();
    }

    @Override
    public boolean isAutoIncrement(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int i) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int i) throws SQLException {
        return 0;
    }

    @Override
    public boolean isSigned(int i) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int i) throws SQLException {
        return 20;
    }

    @Override
    public String getColumnLabel(int i) throws SQLException {
        return getColumnName(i);
    }

    @Override
    public String getColumnName(int i) throws SQLException {
        return fieldNames.get(i-1);
    }

    @Override
    public String getSchemaName(int i) throws SQLException {
        return null;
    }

    @Override
    public int getPrecision(int i) throws SQLException {
        return 0;
    }

    @Override
    public int getScale(int i) throws SQLException {
        return 0;
    }

    @Override
    public String getTableName(int i) throws SQLException {
        return "";
    }

    @Override
    public String getCatalogName(int i) throws SQLException {
        return "";
    }

    @Override
    public int getColumnType(int i) throws SQLException {
        Value value = null;
        try {
            value = result.get(i - 1);
        } catch (NullPointerException e) { // TODO: bolt M01 throws NPE if result.next() is not yet called
            if (retainedResult==null) {
                retainedResult = result.retain();
            }
            if (retainedResult.size()>0) {
                value = retainedResult.get(0).get(i - 1);
            }
        }
        return sqlTypeFor(value);
    }

    private int sqlTypeFor(Value value) {
        if ((value == null) || (value instanceof NullValue)) {
            return NULL;
        } else if (value.isBoolean()) {
            return BOOLEAN;
        } else if (value.isFloat()) {
            return FLOAT;
        } else if (value.isIdentity()) {
            return NUMERIC;
        } else if (value.isInteger()) {
            return NUMERIC;
        } else if (value.isList()) {
            return ARRAY;
        } else if (value.isMap()) {
            return STRUCT;
        } else if (value.isNode()) {
            return STRUCT;
        } else if (value.isPath()) {
            return STRUCT;
        } else if (value.isRelationship()) {
            return STRUCT;
        } else if (value.isString()) {
            return VARCHAR;
        } else {
            throw new IllegalArgumentException("dunno know how to handle " + value);
        }
    }

    @Override
    public String getColumnTypeName(int i) throws SQLException {
        return JDBC_TYPE_MAP.get(getColumnType(i));
    }

    @Override
    public boolean isReadOnly(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int i) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return (T) this;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
