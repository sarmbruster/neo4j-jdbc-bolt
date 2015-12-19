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
import org.neo4j.driver.v1.util.TestNeo4j
import spock.lang.Specification

import java.sql.*

class Neo4jDatabaseMetaDataSpec extends Specification {

    @Rule
    public TestNeo4j neo4j = new TestNeo4j()

    private Connection conn

    def setup() {
        conn = DriverManager.getConnection(DriverSpec.JDBC_BOLT_URL);
        conn.createStatement().execute("create (:Person{name:'John'})");
    }

    def "get schemas"() {
        when:
        ResultSet rs = conn.getMetaData().getSchemas()

        then:
        !rs.next()
    }

    def "get tables"() {
        when:
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
         "REF_GENERATION"].every {
            rs.getString(it); true;
        } // does not throw exception
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
        when:
        ResultSet rs = conn.getMetaData().getColumns( null, null, "%", null );
        def keys = []
        def meta = rs.getMetaData();
        for (int i=1; i<=meta.getColumnCount(); i++) {
            keys << meta.getColumnName(i)
        }
        
        then:
        keys == ["TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "DATA_TYPE", "TYPE_NAME", "COLUMN_SIZE", "BUFFER_LENGTH", "DECIMAL_DIGITS", "NUM_PREC_RADIX", "NULLABLE", "REMARKS", "COLUMN_DEF", "SQL_DATA_TYPE", "SQL_DATETIME_SUB", "CHAR_OCTET_LENGTH", "ORDINAL_POSITION", "IS_NULLABLE", "SCOPE_CATALOG", "SCOPE_SCHEMA", "SCOPE_TABLE", "SOURCE_DATA_TYPE", "IS_AUTOINCREMENT"]

        when:
        def rows = []
        while (rs.next()) {
            def row=[]
            for (int i=1; i<=meta.getColumnCount(); i++) {
                row << rs.getString(i)
            }
            rows << row
        }

        then:
        rows == [
                [null, null, "Person", "name", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]

        ]
    }

    def "after getting metadata iterating the resultset does work"() {

        when: "running statement and asking for column type"
        Statement statement = conn.createStatement()
        ResultSet rs = statement.executeQuery("match (n) return n")
        def columnType = rs.metaData.getColumnType(1)
//        def update = statement.updateCount
        def size = 0
        while (rs.next()) {
            size ++
        }

        then:
//        update == 0
        columnType == Types.STRUCT
        size == 1
    }

}
