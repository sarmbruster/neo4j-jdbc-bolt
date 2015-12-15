package org.neo4j.jdbc.meta

import spock.lang.Specification

/**
 * @author Stefan Armbruster
 */
class ListResultSetSpec extends Specification {

    def "empty resultset"() {
        when:
        def cut = new ResultSetBuilder().column("col1").newResultSet(null);

        then:
        !cut.next()

    }
}
