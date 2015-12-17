package org.neo4j.jdbc.meta;

import org.neo4j.driver.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author Stefan Armbruster
 */
public class MetaResult implements Result {

    private final List<String> columnNames;
    private final List<List<Value>> data;
    private final Iterator<List<Value>> iterator;
    private List<Value> currentRow;

    public MetaResult(List<String> columnNames, List<List<Value>> data) {
        this.columnNames = columnNames;
        this.data = data;
        this.iterator = data.iterator();
    }

    @Override
    public ReusableResult retain() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean next() {
        boolean hasNext = iterator.hasNext();
        if (hasNext) {
            currentRow = iterator.next();
        }
        return hasNext;
    }

    @Override
    public Value get(int fieldIndex) {
        return currentRow.get(fieldIndex);
    }

    @Override
    public Value get(String fieldName) {
        int index = columnNames.indexOf(fieldName);
        return currentRow.get(index);
    }

    @Override
    public Iterable<String> fieldNames() {
        return columnNames;
    }

    @Override
    public Record single() {
        Record record = new MetaRecord(columnNames, currentRow);
        if (next()) {
            throw new IllegalStateException("we have more than one row");
        }
        return record;
    }

    @Override
    public ResultSummary summarize() {
        throw new UnsupportedOperationException();
    }
}
