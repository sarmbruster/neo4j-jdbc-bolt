package org.neo4j.jdbc

import groovy.sql.Sql
import org.junit.Rule
import org.neo4j.driver.v1.util.TestNeo4j
import spock.lang.Specification

import java.sql.*

class Neo4jStatementSpec extends Specification {

    @Rule
    public TestNeo4j neo4j = new TestNeo4j()

    long nodeId
    Connection conn

    long createNode() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("merge (n:Root {name:'root'}) return id(n) as id");
        rs.next();
        return rs.getLong("id");
    }

    String nodeByIdQuery(long nodeId) {
        "match (n) where id(n)=$nodeId return id(n) as id";
    }

    def setup() {
        conn = DriverManager.getConnection(DriverSpec.JDBC_BOLT_URL);
        nodeId = createNode();
    }

    def "execute a statement"() {
        when:
        ResultSet rs = conn.createStatement().executeQuery(nodeByIdQuery(nodeId));

        then:
        rs.next() == true
        nodeId == rs.getObject("id") as int
        nodeId == rs.getLong("id")
        nodeId == rs.getObject(1) as int
        nodeId == rs.getLong(1)
        rs.next() == false
    }

    def "prepared statement with missing parameter fails"() {
        when:
        PreparedStatement ps = conn.prepareStatement("match (n) where ID(n)={1} return ID(n) as id");
        ResultSet rs = ps.executeQuery()
        rs.next()

        then:
        thrown(SQLException)
    }

    def "perpared statement with parameter"() {
        when:
        PreparedStatement ps = conn.prepareStatement("match (n) where ID(n)={1} return ID(n) as id")
        ps.setLong(1, nodeId)
        ResultSet rs = ps.executeQuery()

        then:
        rs.next() == true
        nodeId == rs.getObject("id") as int
        nodeId == rs.getLong("id")
        nodeId == rs.getObject(1) as int
        nodeId == rs.getLong(1)
        rs.next() == false
    }

    def "create node statement"() {
        when:
        PreparedStatement ps = conn.prepareStatement("create (n:User {name:{1}})")
        ps.setString(1, "test")
        // TODO int count = ps.executeUpdate()
        ps.executeUpdate()

        ResultSet rs = conn.createStatement().executeQuery("match (n:User) where n.name='test' return count(n) as c");

        then:
        rs.next() == true
        1 == rs.getInt("c")
    }

    def "create node with map as param"() {
        when:
        PreparedStatement ps = conn.prepareStatement("create (n:User {1})")
        ps.setObject(1, [name:"test"])
        // TODO int count = ps.executeUpdate();
        ps.executeUpdate()
        ResultSet rs = conn.createStatement().executeQuery("match (n:User) where n.name='test' return count(n) as c")

        then:
        rs.next() == true
        1 == rs.getInt("c")
    }

    def "writes fail on readonly connection"() {
        when:
        conn.setReadOnly(true)
        conn.createStatement().executeUpdate("create (n {name:{1}})")

        then:
        thrown SQLException
    }

    def "check expected exception upon invalid usage of getObject()"() {
        when:
        ResultSet rs = conn.createStatement().executeQuery(nodeByIdQuery(nodeId))

        then:
        rs.next() == true

        when:
        def x = rs.getObject(argument)

        then:
        def e = thrown exception
        println e.getMessage()

        where:
        argument | exception        | message
        0        | SQLDataException | "invalid column index"
        2        | SQLDataException | "invalid column index"
        "foo"    | SQLException     | "invalid column name for result set: foo"
    }

    def "use groovy's SQL"() {
        when:
        def sql = Sql.newInstance(DriverSpec.JDBC_BOLT_URL)
        // in parameterized GSQL, labels and reltypes need to be quoted in backticks
        def r = sql.execute("create (n:`Dummy` {1})", [[name: 'myself']])

        then:
        r == false
        sql.updateCount == 3 // 1 node, 1 label, 1 prop

        when:
        r = sql.executeQuery("match (n:Dummy{name:'myself'}) return count(n) as c")

        then:
        r.next()
        r.getInt("c") == 1
        !r.next()

        when:
        def names = []
        sql.eachRow("match (n:Dummy) return n.name as name", {
            names << it.name
        })

        then:
        names == ["myself"]

        when:
        names = []
        sql.eachRow("match (n:Dummy) return n", {
            names << it.n.value("name").asString()
        })

        then:
        names == ["myself"]
    }
}
