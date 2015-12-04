/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.jdbc

import org.junit.Rule
import org.neo4j.driver.util.TestNeo4j
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class Neo4jDatabaseMetaDataSpec extends Specification {

    @Rule
    public TestNeo4j neo4j = new TestNeo4j()

    private Connection conn

    def setup() {
        conn = DriverManager.getConnection(DriverSpec.JDBC_BOLT_URL);
        conn.createStatement().execute("create (:Person)");
    }

    def "get schemas"() {
        when:
        ResultSet rs = conn.getMetaData().getSchemas()

        then:
        rs.next()

        when: "check existence of specified columns "
        rs.getString("TABLE_SCHEM")
        rs.getString("TABLE_CATALOG")

        then:
        notThrown(SQLException)

        when:
        rs.getString("INVALID_COLUMN")

        then:
        thrown(SQLException)

        and:
        !rs.next()

    }

    def "get tables"() {
        when:
//        createTableMetaData( gdb );
        ResultSet rs = conn.getMetaData().getTables( null, null, "%", null );

        then: "we have a result"
        rs.next()

        and:
        ["TABLE_CAT",
         "TABLE_SCHEM",
         "TABLE_NAME",
         "TABLE_TYPE",
         "REMARKS",
         "TYPE_CAT",
         "TYPE_SCHEM",
         "TYPE_NAME",
         "SELF_REFERENCING_COL_NAME",
         "REF_GENERATION"].every { rs.getString(it); true; } // does not throw exception
        rs.getString("TABLE_NAME") == "Person"
        rs.getString("TABLE_TYPE") == "TABLE"

        and: "no more results"
        !rs.next()
    }

/*
    private void createTableMetaData( GraphDatabaseService gdb )
    {
        Transaction tx = gdb.beginTx();
        try {
            final Node tables = gdb.createNode();
            final Node table = gdb.createNode();
            final Node column = gdb.createNode();
            tx.success();
        } finally {
            tx.close();
        }
    }
*/

    def "get columns"() {
        ResultSet rs = conn.getMetaData().getColumns( null, null, "%", null );

        System.out.println( rs );
    }
}
