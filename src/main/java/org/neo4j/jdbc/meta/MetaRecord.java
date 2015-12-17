package org.neo4j.jdbc.meta;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.util.List;

/**
 * @author Stefan Armbruster
 */
public class MetaRecord implements Record {

    private final List<String> fieldNames;
    private final List<Value> data;

    public MetaRecord(List<String> fieldNames, List<Value> data) {
        this.fieldNames = fieldNames;
        this.data = data;
    }

    @Override
    public Value get(int fieldIndex) {
        return data.get(fieldIndex);
    }

    @Override
    public Value get(String fieldName) {
        int index = fieldNames.indexOf(fieldName);
        return data.get(index);
    }

    @Override
    public Iterable<String> fieldNames() {
        return fieldNames;
    }
}
