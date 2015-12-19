package org.neo4j.jdbc

import org.neo4j.driver.internal.value.StringValue
import org.neo4j.driver.v1.ResultCursor
import org.neo4j.jdbc.impl.ResultSetImpl
import spock.lang.Specification

/**
 * @author Stefan Armbruster
 */
class ResultSetImplSpec extends Specification {

    ResultCursor resultCursor = Mock()

    def "validate maxRows setting is used"() {
        when: "execute next() 10 times"
        def cut = new ResultSetImpl(null, resultCursor, maxRows)
        (1..10).each{ cut.next() }

        then: "check how many of those passed throgh to result"
        cardinality * resultCursor.next()

        where:
        maxRows | cardinality
        0       | 10
        5       | 5
    }

    def "check wasNull method"() {
        when: "mock the resultcursor"
        resultCursor.value(0) >> new StringValue("dummy")
        resultCursor.keys() >> ["v"]
        def cut = new ResultSetImpl(null, resultCursor, -1)

        then:
        cut.wasNull() == true

        when:
        cut.getString(1)

        then:
        cut.wasNull() == false
    }

}
