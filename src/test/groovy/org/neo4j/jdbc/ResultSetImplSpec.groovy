package org.neo4j.jdbc

import org.neo4j.driver.Result
import org.neo4j.jdbc.impl.ResultSetImpl
import spock.lang.Specification

/**
 * @author Stefan Armbruster
 */
class ResultSetImplSpec extends Specification {

    Result result = Mock()

    def "validate maxRows setting is used"() {
        when: "execute next() 10 times"
        def cut = new ResultSetImpl(null, result, maxRows)
        (1..10).each{ cut.next() }

        then: "check how many of those passed throgh to result"
        cardinality * result.next()

        where:
        maxRows | cardinality
        0       | 10
        5       | 5

    }
}
