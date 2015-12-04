package org.neo4j.jdbc.meta;

import java.util.Collection;
import java.util.Map;

/**
 * @author Stefan Armbruster
 */
public interface GraphMetaData {

    Collection<String> getTables(String tableNamePattern);

    Map<String,Collection<String>> getColumnsByTables(String tableNamePattern, String columnNamePattern);
}
