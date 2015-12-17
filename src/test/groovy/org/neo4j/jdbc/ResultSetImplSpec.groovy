package org.neo4j.jdbc

import org.neo4j.driver.Result
import org.neo4j.driver.internal.value.StringValue
import org.neo4j.jdbc.impl.ResultSetImpl
import spock.lang.Specification

/**
 * @author Stefan Armbruster
 */
class ResultSetImplSpec extends Specification {

    Result result = Mock()

    def setup() {
        result.fieldNames() >> []
    }

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

    def "check wasNull method"() {
        when:
        result.get(0) >> new StringValue("dummy")
        def cut = new ResultSetImpl(null, result, -1)

        then:
        cut.wasNull() == true

        when:
        cut.getString(1)

        then:
        cut.wasNull() == false
    }

}
