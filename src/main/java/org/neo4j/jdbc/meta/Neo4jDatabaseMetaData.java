/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 * <p/>
 * This file is part of Neo4j.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.jdbc.meta;

import org.neo4j.driver.Result;
import org.neo4j.jdbc.impl.ConnectionImpl;

import java.sql.*;
import java.util.Collection;
import java.util.Map;

/**
 * Provides metadata about a Neo4j database. Tables are implemented with type nodes, and columns are property nodes
 * attached to those type nodes.
 */
public class Neo4jDatabaseMetaData implements DatabaseMetaData {

    private ConnectionImpl connectionImpl;

    public Neo4jDatabaseMetaData(ConnectionImpl connectionImpl) {
        this.connectionImpl = connectionImpl;
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    @Override
    public String getURL() throws SQLException {
//        return connectionImpl.getURL();
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUserName() throws SQLException {
        throw new UnsupportedOperationException();
//        return connectionImpl.getProperties().getProperty( "user" );
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return true;
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        return "Neo4j";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        Result result = connectionImpl.getSession().run("explain match (n) return n limit 1");
        return result.summarize().plan().arguments().get("version").javaString(); // TODO: change this to Neo4j instance version information when bolt supports it
    }

    @Override
    public String getDriverName() throws SQLException {
        return "Neo4j JDBC driver";
    }

    @Override
    public String getDriverVersion() throws SQLException {
//        return connectionImpl.getDriver().getMajorVersion() + "." + connectionImpl.getDriver().getMinorVersion();
        throw new UnsupportedOperationException();
    }

    @Override
    public int getDriverMajorVersion() {
        throw new UnsupportedOperationException();
//        return connectionImpl.getDriver().getMajorVersion();
    }

    @Override
    public int getDriverMinorVersion() {
        throw new UnsupportedOperationException();
//        return connectionImpl.getDriver().getMinorVersion();
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        return false;
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return true;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        return "\"";
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        return "";
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getStringFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        return null;
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        return false;
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert(int i, int i1) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        return null;
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        return null;
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        return null;
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        return true;
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        return ".";
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxConnections() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        return 0;
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxStatements() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getProcedures(String s, String s1, String s2) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getProcedureColumns(String s, String s1, String s2, String s3) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        ResultSetBuilder rs = new ResultSetBuilder();
        rs.column("TABLE_CAT").
                column("TABLE_SCHEM").
                column("TABLE_NAME").
                column("TABLE_TYPE").
                column("REMARKS").
                column("TYPE_CAT").
                column("TYPE_SCHEM").
                column("TYPE_NAME").
                column("SELF_REFERENCING_COL_NAME").
                column("REF_GENERATION");

        Collection<String> labels = connectionImpl.getGraphMetaData().getTables(tableNamePattern);
        for (String label : labels) {
            rs.row().cell("TABLE_NAME", label).cell("TABLE_TYPE", "TABLE");
        }
        return rs.newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        return new ResultSetBuilder().
                column("TABLE_SCHEM").column("TABLE_CATALOG").
//                row(null, null).newResultSet(connectionImpl);
                newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        return getSchemas();
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        return new ResultSetBuilder().column("TABLE_CAT").row("Default").newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        return new ResultSetBuilder().
                column("TABLE_TYPE").
                row("TABLE").newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern,
                                String columnNamePattern) throws SQLException {
        ResultSetBuilder rs = new ResultSetBuilder();
        rs.column("TABLE_CAT").
                column("TABLE_SCHEM").
                column("TABLE_NAME").
                column("COLUMN_NAME").
                column("DATA_TYPE", Types.INTEGER).
                column("TYPE_NAME").
                column("COLUMN_SIZE", Types.INTEGER).
                column("BUFFER_LENGTH").
                column("DECIMAL_DIGITS", Types.INTEGER).
                column("NUM_PREC_RADIX").
                column("NULLABLE").
                column("REMARKS").
                column("COLUMN_DEF").
                column("SQL_DATA_TYPE").
                column("SQL_DATETIME_SUB").
                column("CHAR_OCTET_LENGTH").
                column("ORDINAL_POSITION").
                column("IS_NULLABLE").
                column("SCOPE_CATALOG").
                column("SCOPE_SCHEMA").
                column("SCOPE_TABLE").
                column("SOURCE_DATA_TYPE").
                column("IS_AUTOINCREMENT");

        Map<String, Collection<String>> labelsAndProps = connectionImpl.getGraphMetaData().getColumnsByTables(tableNamePattern, columnNamePattern);
        for (Map.Entry<String,Collection<String>> entry: labelsAndProps.entrySet()) {
            String label = entry.getKey();
            for (String column: entry.getValue()) {
                rs.row().
//            cell("TABLE_CAT", "Default").
//            cell("TABLE_SCHEM", "Default").
                cell("TABLE_NAME", label).
                cell("COLUMN_NAME", column);
//            cell("COLUMN_SIZE", 256).
//            cell("DECIMAL_DIGITS", 256).
//            cell("DATA_TYPE", Types.VARCHAR). // TODO
//            cell("TYPE_NAME", "VARCHAR")
            }


        }
        return rs.newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getColumnPrivileges(String s, String s1, String s2, String s3) throws SQLException {
        throw new UnsupportedOperationException();
//        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getTablePrivileges(String s, String s1, String s2) throws SQLException {
        throw new UnsupportedOperationException();
//        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getBestRowIdentifier(String s, String s1, String s2, int i, boolean b) throws SQLException {
        throw new UnsupportedOperationException();
//        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getVersionColumns(String s, String s1, String s2) throws SQLException {
        throw new UnsupportedOperationException();
//        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getPrimaryKeys(String s, String s1, String s2) throws SQLException {
        return new ResultSetBuilder().
                column("TABLE_CAT").
                column("TABLE_SCHEM").
                column("TABLE_NAME").
                column("COLUMN_NAME").
                column("KEY_SEQ", Types.SMALLINT).
                column("PK_NAME").
                newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getImportedKeys(String s, String s1, String s2) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getExportedKeys(String s, String s1, String s2) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getCrossReference(String s, String s1, String s2, String s3, String s4,
                                       String s5) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getIndexInfo(String s, String s1, String s2, boolean b, boolean b1) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public boolean supportsResultSetType(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean supportsResultSetConcurrency(int i, int i1) throws SQLException {
        return true;
    }

    @Override
    public boolean ownUpdatesAreVisible(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean ownDeletesAreVisible(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean ownInsertsAreVisible(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean othersUpdatesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean updatesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean deletesAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean insertsAreDetected(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getUDTs(String s, String s1, String s2, int[] ints) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionImpl;
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        return true;
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getSuperTypes(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getSuperTables(String s, String s1, String s2) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getAttributes(String s, String s1, String s2, String s3) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsResultSetHoldability(int i) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        throw new UnsupportedOperationException();
//        return connectionImpl.getVersion().getMajorVersion();
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        throw new UnsupportedOperationException();
//        return connectionImpl.getVersion().getMinorVersion();
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        return 3;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        return 0;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return RowIdLifetime.ROWID_UNSUPPORTED;
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return false;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getFunctions(String s, String s1, String s2) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public ResultSet getFunctionColumns(String s, String s1, String s2, String s3) throws SQLException {
        return new ResultSetBuilder().newResultSet(connectionImpl);
    }

    @Override
    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return (T) this;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }

    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return false;
    }
}
