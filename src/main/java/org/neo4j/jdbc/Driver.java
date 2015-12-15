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
package org.neo4j.jdbc;

import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.jdbc.impl.ConnectionImpl;

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * A JDBC driver for Neo4j Graph Database
 *
 * this driver is a thin wrapper around the new binary remoting protocol for Neo4j, see http://alpha.neohq.net
 * @author Stefan Armbruster
 */
public class Driver implements java.sql.Driver {

    public static final String URL_JDBC = "jdbc:neo4j:";

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectionImpl connect(String url, Properties info) throws SQLException {
        if (acceptsURL(url)) {
            try {
                String boltDriverUrl = url.substring(URL_JDBC.length());
                return new ConnectionImpl(GraphDatabase.driver(boltDriverUrl).session());
            } catch (Neo4jException e) {
                throw new SQLException(e);
            }
        } else {
            throw new SQLException("don't accept url " + url);
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith(URL_JDBC);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 2;  // version 1 is for old-style JDBC from https://github.com/neo4j-contrib/neo4j-jdbc/
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
