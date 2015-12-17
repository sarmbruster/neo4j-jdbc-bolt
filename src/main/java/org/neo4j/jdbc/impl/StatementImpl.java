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
package org.neo4j.jdbc.impl;

import org.neo4j.driver.Result;
import org.neo4j.driver.ResultSummary;
import org.neo4j.driver.UpdateStatistics;
import org.neo4j.driver.internal.SimpleResult;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collections;

/**
 * @author Stefan Armbruster
 */
public class StatementImpl implements Statement {
    protected final ConnectionImpl connection;

    private Result result;
    private int maxRows = 0;
    private ResultSummary summary;

    public StatementImpl(ConnectionImpl connection) {
        this.connection = connection;
    }

    @Override
    public ResultSetImpl executeQuery(String cypher) throws SQLException {
        setResult(connection.executeQuery(cypher, Collections.EMPTY_MAP));
        return getResultSet();
    }

    @Override
    public int executeUpdate(String cypher) throws SQLException {
        executeQuery(cypher);
        return getUpdateCount();
    }

    public ResultSummary getSummary() {
        if (summary==null) {
            summary = result.summarize();
        }
        return summary;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
        this.summary = null;
    }


    protected int count(Result result) {
        int count = 0;
        while (result.next()) {
            count++;
        }
        return count;
    }

    @Override
    public void close() throws SQLException {
        // intentionally empty
        //throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        return maxRows;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        this.maxRows = max;
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void cancel() throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
//        throw new UnsupportedOperationException();
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
//        throw new UnsupportedOperationException();

    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean execute(String cypher) throws SQLException {
        result = connection.executeQuery(cypher, Collections.EMPTY_MAP);
        return true;
    }

    @Override
    public ResultSetImpl getResultSet() throws SQLException {
        return new ResultSetImpl(this, result,  maxRows);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        UpdateStatistics statistics = getSummaryWithoutExhaustingIterator().updateStatistics();
        return statistics.nodesCreated() + statistics.nodesDeleted() +
                statistics.relationshipsCreated() + statistics.relationshipsDeleted() +
                statistics.propertiesSet() +
                statistics.labelsAdded() + statistics.labelsRemoved();
    }

    /**
     * workaround to do {@link Result#summarize()} without exhausting the iterator
     * @return
     */
    private ResultSummary getSummaryWithoutExhaustingIterator() {
        if (result instanceof SimpleResult)  {
            try {
                Field summaryField = SimpleResult.class.getDeclaredField("summary");
                summaryField.setAccessible(true);
                return (ResultSummary) summaryField.get(result);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("cannot access summary of a " + result.getClass().getName());
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clearBatch() throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new UnsupportedOperationException();
//        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public ResultSetImpl getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException();
//        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException();
//        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
//        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
//        return false;
    }
}
