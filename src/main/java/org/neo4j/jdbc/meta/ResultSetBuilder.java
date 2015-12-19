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

import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.internal.InternalResultCursor;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.jdbc.impl.ResultSetImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Helper to build ResultSets using a fluent DSL approach.
 */
public class ResultSetBuilder {
    private List<String> columns = new ArrayList<>();
    private List<Value> currentRow = null;
    final private List<Record> body = new ArrayList<>();

    public ResultSetBuilder column(String name) {
        columns.add(name);
        return this;
    }

    public ResultSetBuilder rowData(Collection<Object> values) {

        amendRow();
        currentRow = new ArrayList<>();
        for (Object obj: values) {
            currentRow.add(Values.value(obj));
        }
        for (int i = currentRow.size(); i < columns.size(); i++) {
            currentRow.add(null);
        }
        return this;
    }

    private void amendRow() {
        if (currentRow!=null) {
            body.add(new InternalRecord(columns, null, currentRow.toArray(new Value[currentRow.size()])));
        }
        currentRow = null;
    }

    public ResultSetBuilder row(Object... values) {
        return rowData(Arrays.asList(values));
    }

    public ResultSetBuilder cell(String name, Object obj) {
        int i = columns.indexOf(name);
        if (i == -1) {
            throw new IllegalArgumentException("No such column declared:" + name);
        }
        currentRow.set(i, Values.value(obj));
        return this;
    }

    public ResultSet newResultSet(Connection connection) throws SQLException {
        amendRow();
        return new ResultSetImpl(new InternalResultCursor(columns, body, null));
//        return new ResultSetImpl(new MetaResult(columns, data));
    }

}
