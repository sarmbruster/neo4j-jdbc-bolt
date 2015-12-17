package org.neo4j.jdbc.meta;

import org.neo4j.driver.internal.value.ValueAdapter;

/**
 * @author Stefan Armbruster
 */
public class NullValue extends ValueAdapter {

    @Override
    public String javaString() {
        return null;
    }

}