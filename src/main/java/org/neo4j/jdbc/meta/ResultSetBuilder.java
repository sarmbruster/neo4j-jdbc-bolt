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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Helper to build ResultSets using a fluent DSL approach.
 */
public class ResultSetBuilder
{
    private List<Neo4jColumnMetaData> columns = new ArrayList<Neo4jColumnMetaData>();

    private List<List<Object>> data = new ArrayList<List<Object>>();

    private List<Object> currentRow = new ArrayList<Object>();

    public ResultSetBuilder column( String name, int type )
    {
        String typeName = null;
        if ( type == Types.VARCHAR )
        {
            typeName = String.class.getName();
        }
        else if ( type == Types.INTEGER )
        {
            typeName = Integer.class.getName();
        }

        columns.add( new Neo4jColumnMetaData( name, typeName, type ) );
        return this;
    }

    public ResultSetBuilder column( String name )
    {
        return column( name, Types.VARCHAR );
    }

    public ResultSetBuilder rowData( Collection<Object> values )
    {
        currentRow = new ArrayList<Object>();
        currentRow.addAll( values );

        for ( int i = currentRow.size(); i < columns.size(); i++ )
        {
            currentRow.add( null );
        }

        data.add( currentRow );
        return this;
    }

    public ResultSetBuilder row( Object... values )
    {
        return rowData( Arrays.asList( values ) );
    }

    public ResultSetBuilder cell( String name, Object value )
    {
        int i = getColumnIndex( name );
        if ( i == -1 )
        {
            throw new IllegalArgumentException( "No such column declared:" + name );
        }
        currentRow.set( i, value );
        return this;
    }

    public ResultSet newResultSet( Connection connection ) throws SQLException
    {
        return new ListResultSet( columns, data );
    }

    private int getColumnIndex( String name )
    {
        for ( int i = 0; i < columns.size(); i++ )
        {
            Neo4jColumnMetaData columnMetaData = columns.get( i );
            if ( columnMetaData.getName().equals( name ) )
            {
                return i;
            }
        }
        return -1;
    }
}
