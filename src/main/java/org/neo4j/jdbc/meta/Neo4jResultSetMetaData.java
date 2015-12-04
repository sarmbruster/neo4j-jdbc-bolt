/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.jdbc.meta;

import org.neo4j.jdbc.Neo4jColumnMetaData;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 */
class Neo4jResultSetMetaData
        implements ResultSetMetaData
{
    private List<Neo4jColumnMetaData> columns;

    Neo4jResultSetMetaData( List<Neo4jColumnMetaData> columns )
    {
        this.columns = columns;
    }

    @Override
    public int getColumnCount() throws SQLException
    {
        return columns.size();
    }

    @Override
    public boolean isAutoIncrement( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public boolean isCaseSensitive( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public boolean isSearchable( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public boolean isCurrency( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public int isNullable( int i ) throws SQLException
    {
        return 0;
    }

    @Override
    public boolean isSigned( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public int getColumnDisplaySize( int i ) throws SQLException
    {
        return 20;
    }

    @Override
    public String getColumnLabel( int i ) throws SQLException
    {
        return columns.get( i - 1 ).getName();
    }

    @Override
    public String getColumnName( int i ) throws SQLException
    {
        return columns.get( i - 1 ).getName();
    }

    @Override
    public String getSchemaName( int i ) throws SQLException
    {
        return "Default";
    }

    @Override
    public int getPrecision( int i ) throws SQLException
    {
        return 0;
    }

    @Override
    public int getScale( int i ) throws SQLException
    {
        return 0;
    }

    @Override
    public String getTableName( int i ) throws SQLException
    {
        return null;
    }

    @Override
    public String getCatalogName( int i ) throws SQLException
    {
        return "Default";
    }

    @Override
    public int getColumnType( int i ) throws SQLException
    {
        return columns.get( i - 1 ).getDataType();
    }

    @Override
    public String getColumnTypeName( int i ) throws SQLException
    {
        return columns.get( i - 1 ).getTypeName();
    }

    @Override
    public boolean isReadOnly( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public boolean isWritable( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable( int i ) throws SQLException
    {
        return false;
    }

    @Override
    public String getColumnClassName( int i ) throws SQLException
    {
        return columns.get( i - 1 ).getTypeName();
    }

    @Override
    public <T> T unwrap( Class<T> tClass ) throws SQLException
    {
        return (T) this;
    }

    @Override
    public boolean isWrapperFor( Class<?> aClass ) throws SQLException
    {
        return false;
    }
}
