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

import java.sql.SQLException;
import java.util.List;

/**
 * ResultSet implementation that is backed by Lists.
 */
public class ListResultSet extends AbstractResultSet
{
    private static final int BEFORE_FIRST = -1;
    private int current = -1;
    private List<List<Object>> data;
    private final int rows;

    public ListResultSet(List<Neo4jColumnMetaData> columns, List<List<Object>> data)
    {
        super( columns );
        this.data = data;
        rows = this.data.size();
    }

    @Override
    public boolean next() throws SQLException
    {
        // todo relative(1)
        current++;

        return rows > current;
    }

    @Override
    protected Object[] currentRow()
    {
        return data.get( current ).toArray();
    }

    @Override
    public boolean isBeforeFirst() throws SQLException
    {
        return current == BEFORE_FIRST;
    }

    @Override
    public boolean isAfterLast() throws SQLException
    {
        return current >= rows;
    }

    @Override
    public boolean isFirst() throws SQLException
    {
        return current == 0;
    }

    @Override
    public boolean isLast() throws SQLException
    {
        return current == rows - 1;
    }

    @Override
    public void beforeFirst() throws SQLException
    {
        current = BEFORE_FIRST;
    }

    @Override
    public void afterLast() throws SQLException
    {
        current = rows;
    }

    @Override
    public boolean first() throws SQLException
    {
        current = 0;
        return true;
    }

    @Override
    public boolean last() throws SQLException
    {
        current = rows - 1;
        return true;
    }

    @Override
    public int getRow() throws SQLException
    {
        return current + 1;
    }

    @Override
    public boolean absolute( int i ) throws SQLException
    {
        if ( i > 0 )
        {
            current = i - 1;
        }
        else
        {
            current = rows - i;
        }

        return false;
    }

    @Override
    public boolean relative( int i ) throws SQLException
    {
        if ( current + i >= 0 && current + i < rows )
        {
            current += i;
            return true;
        }
        return false;
    }

    @Override
    public boolean previous() throws SQLException
    {
        return relative( -1 );
    }

    @Override
    public String toString()
    {
        String result = super.toString();
        for ( List<Object> row : data )
        {
            result += "\n" + row;
        }
        return result;
    }

}
