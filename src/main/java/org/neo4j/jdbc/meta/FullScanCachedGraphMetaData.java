package org.neo4j.jdbc.meta;

import org.neo4j.driver.v1.Function;
import org.neo4j.driver.v1.ResultCursor;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Value;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Naive way to extract meta data from the graph: we do a full graph scan
 * WARNING: this is ridiculous slow for large graphs
 */
public class FullScanCachedGraphMetaData implements GraphMetaData {

    private List<String> labels;
    public Map<String, List<String>> labelsAndProps;
    private Session session;

    public FullScanCachedGraphMetaData(Session session) {
        this.session = session;
    }

    @Override
    public Collection<String> getTables(String tableNamePattern) {

        if (labels == null) {
            ResultCursor resultCursor = session.run("match (n) unwind labels(n) as l with distinct l as label return label order by label");
            labels = new ArrayList<>();
            while (resultCursor.next()) {
                labels.add(resultCursor.value("label").asString());
            }
        }

        return filterList(labels, tableNamePattern);
    }

    @Override
    public Map<String, Collection<String>> getColumnsByTables(String tableNamePattern, String columnNamePattern) {
        if (labelsAndProps==null) {
            ResultCursor resultCursor = session.run("match (n) unwind labels(n) as label unwind keys(n) as p return label, collect(distinct p) as props");
            labelsAndProps = new HashMap<>();
            while (resultCursor.next()) {
                labelsAndProps.put(resultCursor.value("label").asString(),
                        resultCursor.value("props").asList(new Function<Value, String>() {
                            @Override
                            public String apply(Value value) {
                                return value.asString();
                            }
                        }) );
            }
        }

        Collection<String> filteredLabels = filterList(labelsAndProps.keySet(), tableNamePattern);
        Map<String, Collection<String>> filtered = new HashMap<>();
        for (String label: filteredLabels) {
            filtered.put(label, filterList(labelsAndProps.get(label), columnNamePattern));
        }
        return filtered;
    }

    private Collection<String> filterList(Collection<String> coll, String filter) {
        if (filter == null) {
            return coll;
        } else {
            Pattern pattern = Pattern.compile(filter.replaceAll("%",".*"));
            List<String> filtered = new ArrayList<>();
            for (String item: coll) {
                if (pattern.matcher(item).matches()) {
                    filtered.add(item);
                }
            }
            return filtered;
        }
    }
}
