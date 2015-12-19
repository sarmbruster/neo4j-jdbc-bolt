package org.neo4j.jdbc

import org.junit.Rule
import org.neo4j.driver.v1.util.TestNeo4j
import spock.lang.Specification

import java.sql.DriverManager
import java.sql.SQLException

class DriverSpec extends Specification {

    static String JDBC_BOLT_URL = "jdbc:neo4j:bolt://localhost";

    @Rule
    public TestNeo4j neo4j = new TestNeo4j()

    Driver driver;

    def setup() {
        driver = new Driver()
    }

    def "acceptance of urls"() {
        expect:
        accepted == driver.acceptsURL(url)

        where:
        url                            | accepted
        JDBC_BOLT_URL                  | true
        "jdbc:derby://localhost:7474/" | false
    }

    def "positive connection check"()
    {
        when:
        def result = driver.connect(JDBC_BOLT_URL, new Properties())

        then:
        noExceptionThrown()
        result != null
    }

    def "negative connection check"() {
        when:
        def result = driver.connect("jdbc:derby://localhost:7474/", new Properties())

        then:
        def e = thrown(SQLException)
        e.message == "don't accept url jdbc:derby://localhost:7474/"

    }

    def "check driver registration"() {
        when:
        def driver = DriverManager.getDriver(JDBC_BOLT_URL)

        then:
        driver != null
        driver.class == Driver.class
    }

    def "can load driver via serviceloader"() {
        when:
        def serviceLoader = ServiceLoader.load( java.sql.Driver.class )

        then:
        serviceLoader.any {
            Driver.class.isInstance(it)
        }

    }
}
