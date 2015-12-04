package org.neo4j.jdbc.meta

import org.junit.Rule
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.util.TestNeo4j
import spock.lang.Specification

/**
 * @author Stefan Armbruster
 */
class FullScanCachedGraphMetaDataSpec extends Specification {

    @Rule
    public TestNeo4j neo4j = new TestNeo4j()

    GraphMetaData meta

    def setup() {
        def driver = GraphDatabase.driver("bolt://localhost")
        def session = driver.session()
        session.run("create (:Person {lastName:'Doe', firstName:'John', dob:123})")
        session.run("create (:Movie {title:'The Matrix'})")
        session.run("create (:Movie {title:'Matrix Reloaded', relased:1999})")
        meta = new FullScanCachedGraphMetaData(session)
    }


    def "filter list"() {
        when:
        def filtered = meta.filterList(list, filter)

        then:
        filtered == result

        where:
        list | filter | result
        ["Adam", "Anna"] | null | ["Adam", "Anna"]
        ["Adam", "Anna", "Berta"] | "A%" | ["Adam", "Anna"]
        ["Adam", "Anna", "Berta"] | "A%a" | ["Anna"]
        ["Adam", "Anna", "Berta"] | "%a" | ["Anna", "Berta"]
    }

    def "get meta data"() {
        when:
        def r = meta.getColumnsByTables(tableNamePattern, columnNamePattern)

        then:
        r == result

        where:
        tableNamePattern | columnNamePattern | result
        null             | null              | [Movie: ["title", "relased"], Person: ["lastName", "firstName", "dob"]]
        "%"              | "%"               | [Movie: ["title", "relased"], Person: ["lastName", "firstName", "dob"]]
        "M%"             | null              | [Movie: ["title", "relased"]]
        "P%%"            | "%Name"           | [Person: ["lastName", "firstName"]]
    }
}
